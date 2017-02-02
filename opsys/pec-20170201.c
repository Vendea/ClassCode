#include <stdio.h>
#include <unistd.h>
#include <sys/types.h>
#include <sys/stat.h>
#include <fcntl.h>
#include <stdlib.h>
#include <sys/wait.h>
#include <errno.h>
#include <string.h>

int
main(int argc, char *argv[]) {
  int total = 0;

  if ( argc == 1 ) {
    fprintf(stderr, "Usage: %s <file> ...\n", argv[0]);
    exit(1);
  }

  int nfiles = argc - 1;
  char **files = &(argv[1]);

  for ( int i = 0 ; i < nfiles ; i++ ) {

    int pid;
    if ( (pid = fork()) == 0 ) {
      // Child
      int nes = 0;
      char buf;

      int fd = open(files[i], O_RDONLY);
      
      if ( fd < 0 ) {
	exit(errno);
      }

      while ( read(fd, &buf, 1) > 0 ) {
	if ( buf == 'e' ) {
	  nes = nes + 1;
	}
      }

      printf("%10d %s\n", nes, files[i]);
      total = total + nes;

      exit(0);
    }
    else if ( pid < 0 ) {
      fprintf(stderr, "%s: Can't fork new child: ", argv[0]);
      perror(NULL);
      exit(1);
    }
  }

  int status, pid;
  while ( (pid = wait(&status)) > 0 ) {
    if ( status != 0 ) {
      fprintf(stderr, "%s: %s\n", argv[0], strerror(status));
    }
  }

  printf("%10d total\n", total);
  exit(0);
}
