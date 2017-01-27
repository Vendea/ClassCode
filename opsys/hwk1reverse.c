#include <stdio.h>
#include <unistd.h>
#include <sys/types.h>
#include <sys/stat.h>
#include <fcntl.h>

void reverse(char* word, int length);
//int str_len(char* word);
#define BUFSZ 1

int main(){
	char buf;
	int fd;
	int nchar;
	if((fd = open("/usr/share/dict/american-english", O_RDONLY)) != -1){
	
		char toread[128];
		char toprint[128];
		int count = 0;
		while((nchar = read(fd, &buf, BUFSZ)) > 0){
			if(buf != '\n'){
				toread[count] = buf;
				count++;
			}
			else{
				for(int i = 0; i < count; i++){
					toprint[i] = toread[count-(i+1)];
				}
				toprint[count] = '\0';
				printf("%s\n", toprint);
				count = 0;
			}
		}
	}
	else {
		printf("error reading file\n");
	}
}

void reverse(char* word, int length){
	char *begin, *end, temp;
	begin = word;
	end = word;

	for(int i = 0; i < length-1; i++){
		end++;
	}
	
	for(int i = 0; i < length/2; i++){
		temp = *end;
		*end = *begin;
		*begin = temp;
		begin++;
		end--;
	}
	word[length] = '\0';
}
