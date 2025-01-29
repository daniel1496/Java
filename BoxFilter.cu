
#include "cuda_runtime.h"
#include "device_launch_parameters.h"
#include <vector>
#include <stdio.h>
//#include "lodepng.h"
#include <png.h>

#include <unistd.h>
#include <stdlib.h>
#include <stdio.h>
#include <string.h>
#include <stdarg.h>

#define PNG_DEBUG 3
#include <png.h>

/**
 * Because program need to read and write png file, so need to use libpng.
 * Below code of read/write png file is referenced from link http://zarb.org/~gc/html/libpng.html
 * I modify it a little bit to suitable for using CUDA
 */

png_byte color_type;
png_byte bit_depth;

png_structp png_ptr;
png_infop info_ptr;
int number_of_passes;
png_bytep * row_pointers;


void read_png_file(const char* filename, unsigned char * &pImage, unsigned &width, unsigned & height)
{
	char header[8];    // 8 is the maximum size that can be checked
	int y;

	/* open file and test for it being a png */
	FILE *fp = fopen(filename, "rb");

	fread(header, 1, 8, fp);


	/* initialize stuff */
	png_ptr = png_create_read_struct(PNG_LIBPNG_VER_STRING, NULL, NULL, NULL);

	info_ptr = png_create_info_struct(png_ptr);


	setjmp(png_jmpbuf(png_ptr));


	png_init_io(png_ptr, fp);
	png_set_sig_bytes(png_ptr, 8);

	png_read_info(png_ptr, info_ptr);

	width = png_get_image_width(png_ptr, info_ptr);
	height = png_get_image_height(png_ptr, info_ptr);
	color_type = png_get_color_type(png_ptr, info_ptr);
	bit_depth = png_get_bit_depth(png_ptr, info_ptr);

	number_of_passes = png_set_interlace_handling(png_ptr);
	png_read_update_info(png_ptr, info_ptr);

	setjmp(png_jmpbuf(png_ptr));

	row_pointers = (png_bytep*) malloc(sizeof(png_bytep) * height);
	for (y=0; y<height; y++)
			row_pointers[y] = (png_byte*) malloc(png_get_rowbytes(png_ptr,info_ptr));

	png_read_image(png_ptr, row_pointers);

	fclose(fp);

	// setup continuous 1D array
	pImage = (unsigned char *) malloc(width * height * 4);
	for (y = 0; y < height; y++)
		memcpy(pImage + y * (width *4), row_pointers[y], width *4);

}


void write_png_file(const char* filename, unsigned char * pImage, unsigned width, unsigned height) {

	int y;
	FILE *fp = fopen(filename, "wb");

	/* initialize stuff */
	png_ptr = png_create_write_struct(PNG_LIBPNG_VER_STRING, NULL, NULL, NULL);

	info_ptr = png_create_info_struct(png_ptr);

	setjmp(png_jmpbuf(png_ptr));

	png_init_io(png_ptr, fp);


	/* write header */
	setjmp(png_jmpbuf(png_ptr));

	png_set_IHDR(png_ptr, info_ptr, width, height,
				 bit_depth, color_type, PNG_INTERLACE_NONE,
				 PNG_COMPRESSION_TYPE_BASE, PNG_FILTER_TYPE_BASE);

	png_write_info(png_ptr, info_ptr);


	/* write bytes */
	setjmp(png_jmpbuf(png_ptr));


	for (y = 0; y < height; y++)
		memcpy(row_pointers[y], pImage + y * (width *4), width *4);

	png_write_image(png_ptr, row_pointers);


	/* end write */
	setjmp(png_jmpbuf(png_ptr));

	png_write_end(png_ptr, NULL);

	/* cleanup heap allocation */
	for (y=0; y<height; y++)
			free(row_pointers[y]);
	free(row_pointers);

	fclose(fp);
}


/**
 * Box filter function use CPU only
 */
void boxFilterCPU(unsigned char * pImageIn, unsigned char * pImageOut, int width, int height, int kernelSize)
{
	int kernelRadius = kernelSize / 2;
	for (int y = 0; y < height; y++)
		for (int x = 0; x < width; x++)
		{
			unsigned sumR = 0;
			unsigned sumG = 0;
			unsigned sumB = 0;
			unsigned count = 0;

			for (int k_y = -kernelRadius; k_y <= kernelRadius; k_y++)
			{
				for (int k_x = -kernelRadius; k_x <= kernelRadius; k_x++)
				{

					if ((y + k_y >= 0 && y + k_y < height) && (x + k_x >= 0 && x + k_x < width))
					{
						count++;
						sumR += pImageIn[4 * width * (y + k_y) + 4 * (x + k_x) + 0];
						sumG += pImageIn[4 * width * (y + k_y) + 4 * (x + k_x) + 1];
						sumB += pImageIn[4 * width * (y + k_y) + 4 * (x + k_x) + 2];
					}
				}
			}

			pImageOut[4 * width * y + 4 * x + 0] = sumR / count;
			pImageOut[4 * width * y + 4 * x + 1] = sumG / count;
			pImageOut[4 * width * y + 4 * x + 2] = sumB / count;
			pImageOut[4 * width * y + 4 * x + 3] = pImageIn[4 * width * y + 4 * x + 3];
		}
}


/**
 * Box filter kernel function
 */
__global__ void boxFilterKernel(unsigned char * pImageIn, unsigned char * pImageOut, unsigned width, unsigned height, int kernelRadius)
{
	int x = blockDim.x * blockIdx.x + threadIdx.x;    // compute index x,y of pixel processed by current thread
	int y = blockDim.y * blockIdx.y + threadIdx.y;

	if ( x < width && y < height)			// make sure x,y are not out of image range, because index of x,y can be larger than image range
	{
		unsigned int sumR = 0;		    // sum of RED value of neightbourhood of pixel at x,y, incldue pixel x,y
		unsigned int sumG = 0;			// sum of GREEN value of neightbourhood of pixel at x,y, incldue pixel x,y
		unsigned int sumB = 0;			// sum of BLUE value of neightbourhood of pixel at x,y, incldue pixel x,y
		unsigned int count = 0;        // number of neighbourhood of pixel at x,y, include pixel x,y

		for (int k_y = -kernelRadius; k_y <= kernelRadius; k_y++)		// loop to all neighbourhood pixel
		{
			for (int k_x = -kernelRadius; k_x <= kernelRadius; k_x++)
			{

				if ((y + k_y >= 0 && y + k_y < height) && (x + k_x >= 0 && x + k_x < width))		// make sure neightbourhood pixel still in image's range
				{
					count++;
					sumR += pImageIn[4 * width * (y + k_y) + 4 * (x + k_x) + 0];		// sum up all neighbourhood value
					sumG += pImageIn[4 * width * (y + k_y) + 4 * (x + k_x) + 1];
					sumB += pImageIn[4 * width * (y + k_y) + 4 * (x + k_x) + 2];
				}
			}
		}

		// Update output value for all channels at pixel x,y
		pImageOut[4 * width * y + 4 * x + 0] = (unsigned char) (sumR / count);
		pImageOut[4 * width * y + 4 * x + 1] = (unsigned char) (sumG / count);
		pImageOut[4 * width * y + 4 * x + 2] = (unsigned char) (sumB / count);
		pImageOut[4 * width * y + 4 * x + 3] = pImageIn[4 * width * y + 4 * x + 3];   // Update Alpha value from input image.
	}
}

/**
 * Box filter function use GPU
 */
void boxFilterGPU(unsigned char * pImageIn, unsigned char * pImageOut, unsigned width, unsigned height, unsigned kernelSize)
{
	unsigned char * dev_pImageOut;        // Declare buffer of input image on device
	unsigned char * dev_pImageIn;		  // Declare buffer of output image on device

	int numElement = width * height * 4;  // compute size of image in order to allocate memory on device

	// Allocate device memory on GPU
	cudaMalloc((void**)& dev_pImageIn, numElement);			// Allocate memory on device for input image
	cudaMalloc((void**)& dev_pImageOut, numElement);        // Allocate memory on device for output image

	// Transfer data from CPU to GPU
	cudaMemcpy(dev_pImageIn, pImageIn, numElement, cudaMemcpyHostToDevice);    // Copy input image from CPU to GPU memory.

	int kernelRadius = kernelSize / 2;
	// Setup grid, block size to launch kernel
	const int blockSizeX = 32;
	const int blockSizeY = 32;
	dim3 blocks(blockSizeX, blockSizeY);   // Image width and height will divide into blocks of 32x32 pixels


	dim3 grid(width / blockSizeX + 1, height / blockSizeY + 1);     // Compute number of blocks

	boxFilterKernel<<<grid, blocks>>>(dev_pImageIn, dev_pImageOut, width, height, kernelRadius);  // Launch kernel for processing

	// Transfer result data from device to host
	cudaMemcpy(pImageOut, dev_pImageOut, numElement, cudaMemcpyDeviceToHost);

	// Free cuda device memory
	cudaFree(dev_pImageIn);
	cudaFree(dev_pImageOut);
}


int main(int argc, char *argv[])
{
	unsigned char * pImage;			// buffer to store input image
	unsigned char * pImageOut;		// buffer to store output image
	unsigned width;					// image width
	unsigned height;				// image height

	read_png_file(argv[1], pImage, width, height);   		// read png file into input buffer


	pImageOut = new unsigned char[width * height * 4];		// Allocate buffer of output image

	unsigned kernelSize = 5;								// kernel size, can change this to any positive odd number

	boxFilterGPU(pImage, pImageOut, width, height, kernelSize);		//call box filtering function, can use CPU or GPU function to compare speed.

	write_png_file("Output.png", pImageOut, width, height);	// write png file of output buffer


	delete pImage;											// Cleanup memory after finnishing
	delete pImageOut;

    return 0;
}

