
#include "cuda_runtime.h"
#include "device_launch_parameters.h"
#include <vector>
#include <stdio.h>

#define ENCRYPTED_TEXT_LEN 10
#define PLAIN_TEXT_LEN 4
#define ALPHABET_NUM 26

__device__ void CudaCrypt(char* rawPassword, char * newPassword) {

	newPassword[0] = rawPassword[0] + 2;
	newPassword[1] = rawPassword[0] - 2;
	newPassword[2] = rawPassword[0] + 1;
	newPassword[3] = rawPassword[1] + 3;
	newPassword[4] = rawPassword[1] - 3;
	newPassword[5] = rawPassword[1] - 1;
	newPassword[6] = rawPassword[2] + 2;
	newPassword[7] = rawPassword[2] - 2;
	newPassword[8] = rawPassword[3] + 4;
	newPassword[9] = rawPassword[3] - 4;

	for (int i = 0; i < 10; i++) {
		if (i >= 0 && i < 6) { //checking all lower case letter limits
			if (newPassword[i] > 122) {
				newPassword[i] = (newPassword[i] - 122) + 97;
			} else if (newPassword[i] < 97) {
				newPassword[i] = (97 - newPassword[i]) + 97;
			}
		} else { //checking number section
			if (newPassword[i] > 57) {
				newPassword[i] = (newPassword[i] - 57) + 48;
			} else if (newPassword[i] < 48) {
				newPassword[i] = (48 - newPassword[i]) + 48;
			}
		}
	}
}


/**
 *  Kernel function for cracking app
 */
__global__ void crack_kernel(char *encrypted_text, char* plain_text)
{
	int x = blockIdx.x * blockDim.x + threadIdx.x;
	int y = blockIdx.y * blockDim.y + threadIdx.y;
	int z = blockIdx.z * blockDim.z + threadIdx.z;
	char candidate_text[4];  		// candidate plain text
	char new_encrypted[10];			// new encrypted code from candidate_text

	if (x < 26 && y < 26 && z < 100)		// each threads check 1 combination, make sure combination is valid
	{
			// Convert x,y,z value to candidate text
			candidate_text[0] = 97 + x;				// 'a' has ascii value is 97, x from 0-> 25, so 97 + x can be from 'a' --> 'z'
			candidate_text[1] = 97 + y;
			candidate_text[2] = 48 + (z / 10);      // '0' has ascii value is 48, so this hold first number from '0' --> '9'
			candidate_text[3] = 48 + (z % 10);		// compute second number

			CudaCrypt(candidate_text, new_encrypted);		// encrypt candidate text to new_encrypted

			bool match = true;
			for (int i = 0; i < 10; i++)		// Check if new_encrypted is match with encrypted_text
			{
				if (encrypted_text[i] != new_encrypted[i])  // if any character is different, will not check any more
				{
					match = false;
					break;
				}
			}

			if (match)			// if a solution found, copy solution to result buffer
			{
				plain_text[0] = candidate_text[0];
				plain_text[1] = candidate_text[1];
				plain_text[2] = candidate_text[2];
				plain_text[3] = candidate_text[3];
			}
	}
}

void crack_gpu(char *encrypted_text){
	printf("Cracking encrypted code %s\n", encrypted_text);

	char * dev_encrypted_text;			// declare memory device to store encrypted text
	char * dev_plain_text;				// declare memory device to store result plain text

	char result_text[5];

	// Allocate device memory
	cudaMalloc((void**)&dev_encrypted_text, ENCRYPTED_TEXT_LEN);
	cudaMalloc((void**)&dev_plain_text, PLAIN_TEXT_LEN);

	// Transfer encrypted text to device to cracking
	cudaMemcpy(dev_encrypted_text, encrypted_text, ENCRYPTED_TEXT_LEN, cudaMemcpyHostToDevice);

	dim3 block(8, 8, 16);												// There are 26 x 26 x 100 combinations need to check
																		// These are divide into smaller block of 8x8x16
	dim3 grid(ALPHABET_NUM / 8 + 1, ALPHABET_NUM / 8 + 1, 100 / 16 + 1 );	// Declare number of blocks need to cover all combination

	crack_kernel<<<grid,block>>>(dev_encrypted_text, dev_plain_text); // Launch cracking kernel

	// Transfer crack result from device to host
	cudaMemcpy(result_text, dev_plain_text, PLAIN_TEXT_LEN, cudaMemcpyDeviceToHost);
	result_text[4] = '\0';

	printf("Cracking result string %s\n", result_text);  // Display cracked result

	// Free device memory
	cudaFree(dev_encrypted_text);
	cudaFree(dev_plain_text);

}

#define FILE_NAME "encrypted_password.bin"

int main(int argc, char *argv[]){

    char * filename;                // File name contain encrypted text
    char *encryped_text = NULL;     // encrypted text
    int fsize = 0;                  // size of file, it also length of encrypted text
    FILE *fp;                       // pointer to file

    filename = argv[1];
    fp = fopen(filename, "r");      // open file in reading mode
    if(fp) {
        fseek(fp, 0, SEEK_END);     // move file cursor to end of file
        fsize = ftell(fp);          // get size of file
        rewind(fp);                 // set file cursor to beginning of file, ready for reading

        encryped_text = (char*) malloc(sizeof(char) * fsize);        // Allocate buffer to store content of file
        fread(encryped_text, 1, fsize, fp);                          // read content of file to buffer

        fclose(fp);                                                  // Close file
    }
  crack_gpu(encryped_text);

  free(encryped_text);

  return 0;
}


