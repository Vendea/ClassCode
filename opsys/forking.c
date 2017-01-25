#include <stdio.h>
#include <unistd.h>

int
main(){

	int pid;
	if ( (pid = fork())<0){
		perror("Can't fork");
		exit(1);
		//error
	}
	else if (pid == 0){
		char buf[BUFSZ];
		int fd;
		int nchar;

		fd = open("/home/share/andrews/iliad-pope.txt", O_RDONLY);

		while(1){
			if((nchar = read(fd, buf, BUFSZ-1)) < BUFSZ-1){
				buf[nchar] = '\0';
				printf("%s\n", buf);
				break;
			}
			buf[BUFSZ-1] = '\0';
			printf("%s", buf);
		}
		exit(0);
	}
	else{
		int statsu;
		int pid = wait(&status);
		
		//parent code
	}

}
