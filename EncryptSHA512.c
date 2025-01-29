#include <string.h>
#include <stdio.h>
#include <stdlib.h>
#include <crypt.h>
#include <unistd.h>
/******************************************************************************
  This program is used to set challenges for password cracking programs. 
  Encrypts using SHA-512.
  
  Compile with:
    cc -o EncryptSHA512 EncryptSHA512.c -lcrypt
    
  To encrypt the password "pass":
    ./EncryptSHA512 pass
    
  It doesn't do any checking, just does the job or fails ungracefully.

  Dr Kevan Buckley, University of Wolverhampton, 2017. Modified by Dr. Ali Safaa 2019
******************************************************************************/

#define SALT "$6$AS$"

#define FILE_NAME "encrypted_password.bin"

int main(int argc, char *argv[]){
  
    char * encrypted_str;    
    
    encrypted_str = crypt(argv[1], SALT);
    printf("%s\n", encrypted_str );
  
    // Write encrypted string to file
    FILE * fout;
    fout = fopen(FILE_NAME,"w+");
    fprintf(fout, "%s", encrypted_str);
    fclose(fout);
    
  return 0;
}
