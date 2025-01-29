#include <stdio.h>
#include <string.h>
#include <stdlib.h>
#include <crypt.h>
#include <pthread.h>
#include <unistd.h>

// Number of threads 
#define NUM_THREADS 8   

// Define a structure of arguments which passed to thread function
struct arguments {
    char * salt_and_encrypted;
    int threadi_id;
};

// Global variable to check if valid solution is found, if found then stop other threads from searching
int found = 0;

/******************************************************************************
  Demonstrates how to crack an encrypted password using a simple
  "brute force" algorithm. Works on passwords that consist only of 2 uppercase
  letters and a 2 digit integer.

  Compile with:
    cc -o CrackAZ99 CrackAZ99.c -lcrypt

  If you want to analyse the output then use the redirection operator to send
  output to a file that you can view using an editor or the less utility:
    ./CrackAZ99 > output.txt

  Dr Kevan Buckley, University of Wolverhampton, 2018 Modified by Dr. Ali Safaa 2019
******************************************************************************/

int count=0;     // A counter used to track the number of combinations explored so far

/**
 Required by lack of standard function in C.   
*/

void substr(char *dest, char *src, int start, int length){
  memcpy(dest, src + start, length);
  *(dest + length) = '\0';
}

/**
 * This function is run parallel by multiple threads, each thread in search a portion of solution space.
 * For 100 numbers from 00-> 99 will divided into NUM_THREADS parts, each thread search only on its part.
 **/
void * crackthread(void *arguments) {
    int x, y, z; // Loop counters
    char salt[7]; // String used in hashing the password. Need space for \0 // incase you have modified the salt value, then should modifiy the number accordingly
    char plain[7]; // The combination of letters currently being checked // Please modifiy the number when you enlarge the encrypted password.
    char *enc; // Pointer to the encrypted password
    int tid; // thread id

    int index_s, index_e; // start and end number to search

    int part_size; // size of part which each thread will check


    struct arguments *args = arguments; // cast argument of type void * to struct arguments * so that can access member
    tid = args->threadi_id;             // get tid value from struct argument

    substr(salt, args->salt_and_encrypted, 0, 6);


    part_size = 100 / NUM_THREADS;  // compute part size , 100 numbers from 00 --> 99 divided by number of threads

    // Compute value for start and end number to search
    if (tid < NUM_THREADS - 1) // All threads except last thread
    {
        index_s = tid * part_size;
        index_e = (tid + 1) * part_size - 1;

    } else { // Last thread, fix index_e is alway 99.
        index_s = tid * part_size;
        index_e = 100 - 1;
    }


    for (z = index_s; z <= index_e; z++) {
        for (x = 'A'; x <= 'Z'; x++) {
            for (y = 'A'; y <= 'Z'; y++) {
                if (found == 0) {  // Only check if no solution founded
                    sprintf(plain, "%c%c%02d", x, y, z);
                    enc = (char *) crypt(plain, salt);
                    count++;
                    if (strcmp(args->salt_and_encrypted, enc) == 0) {
                        printf("#%-8d%s %s\n", count, plain, enc);
                        found = 1;
                    }
                } else {   // In case solution founded, just return.
                    pthread_exit(NULL);
                    return NULL;
                    //return;	//uncomment this line if you want to speed-up the running time, program will find you the cracked password only without exploring all possibilites
                }
            }
        }
    }

    pthread_exit(NULL);
    return NULL;
}

/**
 This function can crack the kind of password explained above. All combinations
 that are tried are displayed and when the password is found, #, is put at the 
 start of the line. Note that one of the most time consuming operations that 
 it performs is the output of intermediate results, so performance experiments 
 for this kind of program should not include this. i.e. comment out the printfs.
*/

void crack(char *salt_and_encrypted){
  int tid;         // id of thread
  int return_code; // 
  
  pthread_t threads[NUM_THREADS];  // Declare threads arrays which hold handle of threads
  
  struct arguments args[NUM_THREADS];  // Declare input argument for each thread

  for (tid = 0; tid < NUM_THREADS; tid++) {

    args[tid].salt_and_encrypted = salt_and_encrypted;
    args[tid].threadi_id = tid;

    return_code = pthread_create(&threads[tid], NULL, crackthread, (void *)&args[tid]);  // Launch threads
    
    if (return_code) {
        printf("ERROR; return code from pthread_create() is %d\n", return_code);
        exit(-1);
    }
  }
  
  for (tid = 0; tid < NUM_THREADS; tid++) {
      pthread_join(threads[tid],NULL);  // Wait threads finish
  }
 
}

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
  crack(encryped_text);		//Copy and Paste your ecrypted password here using EncryptShA512 program
  printf("%d solutions explored\n", count);
  free(encryped_text);

  return 0;
}
