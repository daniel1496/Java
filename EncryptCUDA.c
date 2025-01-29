#include <stdio.h>

void CudaCrypt(char* rawPassword, char * newPassword) {

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
	newPassword[10] = '\0';

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


#define FILE_NAME "encrypted_password.bin"

int main(int argc, char *argv[]){

    char encrypted_str[11];

    CudaCrypt(argv[1], encrypted_str);
    printf("%s\n", encrypted_str );

    // Write encrypted string to file
    FILE * fout;
    fout = fopen(FILE_NAME,"w+");
    fprintf(fout, "%s", encrypted_str);
    fclose(fout);

  return 0;
}


