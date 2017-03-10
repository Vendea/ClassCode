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

    int posn = 0;
    int nfiles = argc - optind;
    int fd;
    char *stdin_fname = "-\0";
    char **files = &(argv[optind]);
    if(nfiles == 0){
        count_parts *res = count(0);
        print_stuff(res->lines, res->words, res->bytes, stdin_fname);
        exit(EXIT_SUCESS);
    }
    else if(nfiles > 1){
        total_flag = 1;
    }

    procinfo *plist = NULL;
    int nchild = 0;
    count_parts ind_counts[nfiles];
    for(int i = 0; i < nfiles; i++){
        ind_counts[i] = (count_parts *) malloc(sizeof(count_parts));
    }

    //deal with parent
    while(posn < nfiles || nchild > 0){

        //try to harvest children
        int cpid, status;
        while((cpid = waitpid(-1, &status, WNOHANG)) > 0){
            if(plist == NULL){
                fprintf(stderr, "%s: Child died, no children on list\n", progname);
                continue;
            }

            if(plist->pid == cpid){
                ind_counts[plist->fileno] = //help
            }
        }

        //try to start child process
        if(nchild < nproc && posn < nfiles){
            int fd;
            if(files[posn] == '-'){
                fd = 0;
            }
            else{
                if((fd = open(files[posn], O_RDONLY)) < 0){
                    if(errno == EMFILE || errno == ENFILE){
                        sleep(1);
                        continue;
                    }
                    fprintf(stderr, "%s: %s: %s\n", progname, argv[posn], strerror(errno));
                    files[posn] = '\0';
                    posn = posn + 1;
                    continue;
                }

                int fifo[2];
                if(pipe(fifo) < 0) {
                    close(fd);
                    sleep(1);
                    continue;
                }

                int pid;
                if((pid = fork()) < 0){
                    close(fd);
                    close(fifo[0]);
                    close(fifo[1]);
                    sleep(1);
                    continue;
                }

                if(pid == 0){
                    //we're a child
                    close(fifo[0]);
                    count_parts *res = count(fd);
                    close(fd);

                    if(write(fifo[1], &res, sizeof(count_parts)) != sizeof(count_parts)){
                        close(fifo[1]);
                        exit(EXIT_FAILURE);
                    }
                    close(fifo[1]);
                    exit(EXIT_SUCCESS);
                }

                //else we're a parent
                close(fifo[1]);
            }
        posn++;
    }
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
            if(isspace(*p) && !isspace(prev)){
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
    char *plines = (char *) malloc(sizeof(long));
    char *pwords = (char *) malloc(sizeof(long));
    char *pbytes = (char *) malloc(sizeof(long));
    char *tmp = (char *) malloc(sizeof(long));
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
