#include <stdlib.h>
#include <stdio.h>
#include <unistd.h>
#include <sys/types.h>
#include <sys/stat.h>
#include <fcntl.h>

#define BUFSZ 1

int main(){
	char buf;
	int fd;
	int nchar;
	
	if((fd = open("/usr/share/dict/american-english", O_RDONLY)) != -1){
		char word[128];
		int count = 0;
	        int printnext = 0;
		while((nchar = read(fd, &buf, BUFSZ)) > 0){
			if(buf != '\n'){
				word[count] = buf;
				count++;
			}
		        else{
		                word[count] = '\0';
                		if(printnext){
			                printf("%s\n", word);
               			}
		                printnext = 0;
		                for(int i = 0; i < count-1; i++){
                			if(word[i] == 'e' && word[i+1] == 'e'){
			                        printnext = 1;
                   			}
		                }
		                count = 0;
            		}
		}
	}
	else {
		printf("error reading file\n");
	}
}
