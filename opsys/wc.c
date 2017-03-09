#include <stdarg.h>
#include <stdio.h>
#include <stdlib.h>
#include <getopt.h>
#include <unistd.h>

static int lines_flag = 0;
static int words_flag = 0;
static int bytes_flag = 0;
static int total_flag = 0;
static struct option long_options[] = 
    {
        {"lines", no_argument, 0, 'l'},
        {"words", no_argument, 0, 'w'},
        {"bytes", no_argument, 0, 'c'},

        {NULL, 0, NULL, 0}
    };

char *progname;

void usage();
void process_stdin();
void process_file(char* fname);
void print_stuff(char* lines, char* words, char* bytes, char* fname);

int main(int argc, char *argv[]){
    progname = argv[0];

#ifdef _SC_NPROCESSORS_ONLN
    long nproc = sysconf(_SC_NPROCESSORS_ONLN);
#else
    long nproc = 1;
#endif

    while(1){
        int option_index = 0;
        c = getopt_long(argc, argv, "n:wlc", long_options, &option_index);

        if(c == -1){
            break;
        }

        switch(c){
        case 'l':
            lines_flag = 1;
            break;

        case 'w':
            words_flag = 1;
            break;

        case 'c':
            bytes_flag = 1;
            break;

        case 'n':
            char *endptr;
            long tproc = strtol(optarg, &endptr, 0);
            if(*endptr != '\0' || tproc < 1){
                usage();
            }
            nproc = tproc;
            break;

        default:
            fprintf(stderr, "Usage: wc [wlc][n=#] [file ...]\n");
            exit(EXIT_FAILURE);
        }
    }

    int posn = optind;
    int nfiles = argc - optind;
    if(nfiles == 0){
        process_stdin();
        print_stuff(
    }
        if(argv[posn] == '-'){
            process_stdin();
        }
        else{
            process_file(argv[posn]);
        }
        posn++;
    } while(posn < argc && nchild < )
}

void process_stdin(){
    while(!feof(stdin)){
        
    }
}

void process_file(char* fname){
    while(1){} //something
}

void print_stuff(long lines, long words, long bytes, char* fname){
    char *plines = "";
    char *pwords = "";
    char *pbytes = "";
    char tmp[128];
    int numFlags = 0;
    if(lines_flag){
        snprintf(tmp, "%li", lines);
        plines = tmp;
        numFlags++;
    }
    if(words_flag){
        snprintf(tmp, "%li", words);
        pwords = tmp;
        numFlags++;
    }
    if(bytes_flag){
        snprintf(tmp, "%li", bytes);
        pbytes = tmp;
        numFlags++;
    }
    if(!numFlags){
        snprintf(tmp, "%li", lines);
        plines = tmp;
        snprintf(tmp, "%li", words);
        pwords = tmp;
        snprintf(tmp, "%li", bytes);
        pbytes = tmp;
    }
    fprintf("\n     %s     %s     %s %s\n", plines, pwords, pbytes, fname);
}

void usage(){
    fprintf(stderr, "Usage: %s [-n <nproc>] [-wlc] [<files> ...]\n", progname);
    exit(EXIT_FAILURE);
}
