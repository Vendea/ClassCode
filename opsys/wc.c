#include <stdarg.h>
#include <stdio.h>
#include <stdlib.h>
#include <getopt.h>
#include <unistd.h>
#include <sys/types.h>
#include <sys/stat.h>
#include <sys/wait.h>
#include <fcntl.h>
#include <errno.h>
#include <string.h>
#include <ctype.h>

#define BUFSIZE (16*1024)

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

typedef struct count_parts{
    long lines;
    long words;
    long bytes;
} count_parts;

typedef struct procinfo{
    int pid;
    int fdout;
    int fileno;
    struct procinfo *next;
} procinfo;

char *progname;

void usage();
void process_stdin();
count_parts process_file(char* fname);
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
    else if(nfiles > 1){
        total_flag = 1;
    }
    if(argv[posn] == '-'){
        process_stdin();
    }
    else{
        process_file(argv[posn]);
    }
    while(posn < argc && nchild < )
    posn++;
}

void process_stdin(){
    while(!feof(stdin)){
        
    }
}

count_parts process_file(char* fname){
    //get fd here and pass to count
    //count_parts res = count
    //then print
    //print_stuff(res->lines, res->words, res->bytes, fname);
}

count_parts count(int fd){
    char buf[BUFSIZE +1];
    long lcount = 0;
    long wcount = 0;
    long bcount = 0;
    char prev = ' ';

    posix_fadvise(fd, 0, 0, POSIX_FADV_SEQUENTIAL | POSIX_FADV_NOREUSE);

    int bytes_read;

    while((bytes_read = read(fd, buf, BUFSIZE)) > 0){
        char *p = buf;
        char *end = p + bytes_read;

        while(p != end){
            if(*p == '\n'){
                lcount ++;
            }
            if(isspace(*p) && (isalpha(prev) || isdigit(prev))){
                wcount++;
            }
            bcount += sizeof(char);
            prev = *p;
            *p++;
        }
    }
    count_parts *ret = (count_parts *) malloc(sizof(count_parts));
    ret->lines = lcount;
    ret->words = wcount;
    ret->bytes = bcount;
    return *ret;
}

void print_stuff(long lines, long words, long bytes, char* fname){
    char plines[128];
    char pwords[128];
    char pbytes[128];
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
    if(0 == numFlags){
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
