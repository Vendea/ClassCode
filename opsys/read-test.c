#include <unistd.h>
#include <stdio.h>

#include <sys/types.h>
#include <sys/stat.h>
#include <fcntl.h>

#define BUFSZ 100

int
main() {
	char buf[BUFSZ];
	int fd;
	int nchar;

	if( (fd = open("/tmp/junk.txt", O_WRONLY | O_CREAT);
	
	while(1){
		if( (nchar = read(fd, buf, BUFSZ-1)) < BUFSZ-1 ){
			buf[nchar] = '\0';
			printf("%s\n", buf);
			break;
		}

		buf[BUFSZ-1] = '\0';
		printf("%s\n", buf);
	}
}
