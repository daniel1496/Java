    #include <stdio.h>
#include <stdlib.h>
#include <pthread.h>

#define MAX_MATRIX_NUM 100

// Number of threads 
#define NUM_THREADS 8 

// Define matrix struct to store matrix information
typedef struct matrix_t {
  int iRow;
  int iCol;
  double * arrMatrix;
} Matrix;

// Define a structure of arguments which passed to thread function
struct arguments {
    Matrix * A;
    Matrix * B;
    Matrix * C;
    int start;
    int end;
};

// free matrix memory
void free_mem(Matrix * matrix) {
  free(matrix->arrMatrix);
}

// read data of single matrix from current file position
void read_single_matrix(FILE* fp, Matrix * matrix){
  int r,c;
  double val;
  // read rows and columns value
  fscanf(fp, "%d,%d\n", &matrix->iRow, &matrix->iCol);
  // prepare buffer to load matrix
  matrix->arrMatrix = (double*) malloc(matrix->iRow * matrix->iCol * sizeof(double));

  // read single value of matrix
  for (r = 0; r < matrix->iRow; r++) {
    for( c = 0; c < matrix->iCol -1 ; c++){
      fscanf(fp, "%lf,", &val);
      matrix->arrMatrix[r * matrix->iCol + c] = val;
    }
    fscanf(fp, "%lf\n", &val);
    matrix->arrMatrix[r * matrix->iCol + matrix->iCol -1] = val;
  }
}

void print_single_matrix(Matrix * matrix){
  int r,c;
  // print out matrix
  printf("Matrix size %d x %d\n", matrix->iRow, matrix->iCol);
  for (r = 0; r < matrix->iRow; r++) {
    for( c = 0; c < matrix->iCol; c++){

      printf ("%.2lf ", matrix->arrMatrix[r * matrix->iCol + c]);
    }
    printf ("\n");
  }
  printf ("\n");
}

void save_single_matrix(char * filename, Matrix * matrix){
  int r,c;
  FILE * fp;
  fp = fopen(filename,"w");
  // print out matrix
  fprintf(fp,"%d,%d\n", matrix->iRow, matrix->iCol);

  for (r = 0; r < matrix->iRow; r++) {
    for( c = 0; c < matrix->iCol -1 ; c++){

      fprintf (fp,"%.2lf,", matrix->arrMatrix[r * matrix->iCol + c]);
    }
    fprintf (fp,"%.2lf\n", matrix->arrMatrix[r * matrix->iCol + matrix->iCol -1]);
  }
  fprintf (fp,"\n");

  fclose(fp);
}

void print_info_matrices(Matrix ** matrix, int num_matrix){
  int index;
  for (index = 0; index < num_matrix; index++){
    printf("%d. Matrix size %d x %d\n\n", index + 1, matrix[index]->iRow, matrix[index]->iCol);
  }

}

void read_matrices_from_file(char * file_name, Matrix ***matrices, int * num_matrix) {
  FILE * fp;
  Matrix * matrix;
  int index = 0;
  *matrices = (Matrix**) malloc(MAX_MATRIX_NUM * sizeof(Matrix*));

  fp = fopen(file_name,"r");

  while(!feof(fp)){
    matrix = (Matrix*) malloc(sizeof(Matrix));
    read_single_matrix(fp, matrix);
    (*matrices)[index++] = matrix;

    print_single_matrix(matrix);

  }

  *num_matrix = index;


  fclose(fp);
}


// Peform CPU matrix multiplication
void matrix_mul_cpu(Matrix * A, Matrix * B, Matrix **C)
{
  int iRow, iCol, iK;
  int r,c,k;
  double sum;
  iRow = A->iRow;
  iCol = B->iCol;
  iK = A->iCol;

  (*C)->iCol = iCol;
  (*C)->iRow = iRow;
  (*C)->arrMatrix = (double*) malloc(iRow * iCol * sizeof(double));

  for (r = 0; r < iRow; r++){
    for (c = 0; c < iCol; c++){
      sum = 0;
      for (k = 0; k < iK; k++)
      {
        sum += A->arrMatrix[r * iK + k] * B->arrMatrix[k * iCol + c];
      }
      (*C)->arrMatrix[r * iCol + c] = sum;

    }
  }
}

void * matmul(void *arguments) {

    Matrix * A, * B, *C;
    int start;
    int end;
    int r,c,k;
    double sum;


    struct arguments *args = arguments; // cast argument of type void * to struct arguments * so that can access member
    A = args->A;
    B = args->B;
    C = args->C;
    start = args->start;
    end = args->end;

    for (r = start; r <= end; r++){
      for (c = 0; c < B->iCol; c++){
        sum = 0;
        for (k = 0; k < A->iCol; k++)
        {
          sum += A->arrMatrix[r * A->iCol + k] * B->arrMatrix[k * B->iCol + c];
        }
        C->arrMatrix[r * B->iCol + c] = sum;

      }
    }

    pthread_exit(NULL);
    return NULL;
}

void matrix_mul_pthread(Matrix * A, Matrix * B, Matrix **C){
  int tid;         // id of thread
  int return_code; // 
  int part_size;
  int num_thread = NUM_THREADS;
  pthread_t threads[NUM_THREADS];  // Declare threads arrays which hold handle of threads
  struct arguments args[NUM_THREADS];  // Declare input argument for each thread

  int iRow, iCol, iK;
  iRow = A->iRow;
  iCol = B->iCol;
  iK = A->iCol;

  (*C)->iCol = iCol;
  (*C)->iRow = iRow;
  (*C)->arrMatrix = (double*) malloc(iRow * iCol * sizeof(double));
  
  if (A->iRow < NUM_THREADS)
  {
    num_thread = A->iRow;
  }

  part_size = A->iRow / num_thread ;

  for (tid = 0; tid < num_thread; tid++) {



    args[tid].A = A;
    args[tid].B = B;
    args[tid].C = *C;

        // Compute value for start and end number to search
    if (tid < num_thread - 1) // All threads except last thread
    {
        args[tid].start = tid * part_size;
        args[tid].end = (tid + 1) * part_size - 1;

    } else { // Last thread, fix index_e is alway 99.
        args[tid].start = tid * part_size;
        args[tid].end = A->iRow - 1;
    }
    

    return_code = pthread_create(&threads[tid], NULL, matmul, (void *)&args[tid]);  // Launch threads
    
    if (return_code) {
        printf("ERROR; return code from pthread_create() is %d\n", return_code);
        exit(-1);
    }
  }
  
  for (tid = 0; tid < num_thread; tid++) {
      pthread_join(threads[tid],NULL);  // Wait threads finish
  }
 
}

// Get selection matrix from user 
int get_matrix_index(char * question, int max)
{
  int selection = 0;
  do {
    printf("%s:\n", question);
    scanf("%d", &selection);

    if (selection < 1 || selection > max)
      printf("Invalid input. Please input again\n");
  }
  while ((selection < 1 || selection > max));

  return selection;

}

int main()
{
  Matrix ** matrices = 0;
  int num_matrix = 0;
  int first_matrix = 0 ;
  int second_matrix = 1;
  int i;

  Matrix * result;

  read_matrices_from_file("1822984-matrices.txt", &matrices, &num_matrix);

  // print info about size of matrices which read from file.
  print_info_matrices(matrices, num_matrix);

  // get matrix selection from user
  first_matrix = get_matrix_index("Input index of first matrix (start from 1)", num_matrix);
  second_matrix = get_matrix_index("Input index of second matrix (start from 1)", num_matrix);

  // validate can mutiply
  if (matrices[first_matrix-1]->iCol != matrices[second_matrix-1]->iRow) {
    printf("Can not mutiple matrix size of %d x %d and %d x %d \n",
      matrices[first_matrix-1]->iRow, matrices[first_matrix-1]->iCol, 
      matrices[second_matrix-1]->iRow, matrices[second_matrix-1]->iCol);

  }else
  {
    
    matrix_mul_pthread(matrices[first_matrix-1], matrices[second_matrix-1], &result);
    //print_single_matrix(result);
    save_single_matrix("output_matrix.txt", result);  // save result of mutiplication to file

     free_mem(result);
  }


  // free memory;
  for (i = 0 ; i < num_matrix; i++)
  {
    free_mem(matrices[i]);

  }
 
  return 0;
}
  
