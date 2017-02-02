#include <stdio.h>

int main(int argc, char* argv[]){
    char buf;
    int fd;
    int nes = 0;
    int total = 0;

    if (argc == 1){
        fprintf(stderr, "Usage %s <file> ....\n", argv[0]);
        exit(0);
    }
    
    for(int i = 1; i < argc; i++){
        int fd = open(argv[i], O_RDONLY);

        if(fd<0){
            fprintf(stderr, "%s: ", argv[0]);
            perror(NULL);
            exit(2);
        }
    }

    while( read( fd, &buf, 1) > 0){
        if (buf == 'e'){
            nes++;
        }
    }

    printf("%10d %s\n", nes, fn);
}
