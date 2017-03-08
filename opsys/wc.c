#include <stdio.h>
#include <stdlib.h>
#include <getopt.h>
#include <unistd.h>

static int words_flag = 0;
static int bytes_flag = 0;
static int lines_flag = 0;
static struct option long_options[] = 
    {
        {"words", no_argument, 0, 'w'},
        {"lines", no_argument, 0, 'l'},
        {"bytes", no_argument, 0, 'c'},

        {NULL, 0, NULL, 0}
    };

int main(int argc, char *argv[]){

    int nproc = sysconf(_SC_NPROCESSORS_ONLN);

    while(1){
        int option_index = 0;
        c = getopt_long(argc, argv, "n:wlc", long_options, &option_index);

        if(c == -1){
            break;
        }

        switch(c){
        case 'w':
            words_flag = 1;
            break;

        case 'c':
            bytes_flag = 1;
            break;

        case 'l':
            lines_flag = 1;
            break;

        case 'n':
            nproc = atoi(optarg);
            break;

        default:
            fprintf(stderr, "Usage: wc [wlc][n=#] [file ...]\n");
            exit(EXIT_FAILURE);
        }
    }

}

