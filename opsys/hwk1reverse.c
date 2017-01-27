#include <stdio.h>
#include <unistd.h>
#include <sys/types.h>
#include <sys/stat.h>
#include <fcntl.h>

void reverse(char* word);
int str_len(char* word);
#define BUFSZ 100

int main(){
	char buf[BUFSZ];
	int fd;
	int nchar;

	if((fd = open("/usr/share/dict/american-english", O_RDONLY)) != -1){
	
		while(1){
			char temp[BUFSZ];
			if((nchar = read(fd, buf, BUFSZ-1)) < BUFSZ-1){
				int i = 0;
				while(i < nchar){
					int step = 0;
					while( *(buf+i) != '\n'){
						temp[step] = buf[i];
						step++;
						i++;
					}
					temp[step] = '\0';
					
				}
				printf("%s\n", buf);
				break;
			}
			buf[BUFSZ-1] = '\0';
			printf("%s\n", buf);
		}
	}
	else {
		printf("error reading file\n");
	}
}

void reverse(char* word){
	
}

int str_len(char* word){
	int c = 0;
	while( *(word+c) != '\0'){
		c++;
	}
	return c;
}
