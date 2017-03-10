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
count_parts* count(int fd);
count_parts* readCount(int status, int fd);
void print_stuff(long lines, long words, long bytes, char* fname);

int main(int argc, char *argv[]){
    progname = argv[0];

#ifdef _SC_NPROCESSORS_ONLN
    long nproc = sysconf(_SC_NPROCESSORS_ONLN);
#else
    long nproc = 1;
#endif

    while(1){
        int option_index = 0;
        int c = getopt_long(argc, argv, "n:wlc", long_options, &option_index);
        char *endptr;
        long tproc;

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
            tproc = strtol(optarg, &endptr, 0);
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

    count_parts *total = (count_parts *) malloc(sizeof(count_parts));
    total->lines = 0;
    total->words = 0;
    total->bytes = 0;
    char *t_fname = "total\0";
    int posn = 0;
    int nfiles = argc - optind;
    int fd;
    char *stdin_fname = "-\0";
    char **files = &(argv[optind]);
    if(nfiles == 0){
        count_parts *res = count(0);
        print_stuff(res->lines, res->words, res->bytes, stdin_fname);
        exit(EXIT_SUCCESS);
    }
    if(nfiles > 1){
        total_flag = 1;
    }

    procinfo *plist = NULL;
    int nchild = 0;
    count_parts* ind_counts[nfiles];
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
                ind_counts[plist->fileno] = readCount(status, plist->fdout);

                void *hold = plist;
                plist = plist->next;
                free(hold);
                nchild = nchild - 1;
                continue;
            }

            procinfo *last = plist, *this = plist->next;
            while(this != NULL){
                if(this->pid == cpid){
                    ind_counts[this->fileno] = readCount(status, this->fdout);
                    last->next = this->next;
                    nchild = nchild - 1;

                    free((void *) this);
                    this = last;
                    break;
                }
                last = this;
                this = this->next;
            }
            if(this == NULL){
                fprintf(stderr, "%s: Child exited with no record\n", progname);
                exit(EXIT_FAILURE);
            }
        }

        //try to start child process
        if(nchild < nproc && posn < nfiles){
            int fd;
            if(files[posn][0] == '-'){
                fd = 0;
            }
            else if((fd = open(files[posn], O_RDONLY)) < 0){
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
                exit(EXIT_FAILURE);
            }

            int pid;
            if((pid = fork()) < 0){
                close(fd);
                close(fifo[0]);
                close(fifo[1]);
                sleep(1);
                exit(EXIT_FAILURE);
            }

            if(pid == 0){
                //we're a child
                close(fifo[0]);
                count_parts *res = count(fd);
                close(fd);
                print_stuff(res->lines, res->words, res->bytes, files[posn]);

                if(write(fifo[1], res, sizeof(count_parts)) != sizeof(count_parts)){
                    close(fifo[1]);
                    exit(EXIT_FAILURE);
                }
                close(fifo[1]);
                exit(EXIT_SUCCESS);
            }

            //else we're a parent
            close(fifo[1]);

            procinfo *new = (procinfo *) malloc(sizeof(procinfo));
            new->pid = pid;
            new->fdout = fifo[0];
            new->fileno = posn;
            new->next = plist;
            plist = new;
            nchild = nchild + 1;
            posn = posn + 1;
        }
    }

    if(total_flag){
        for(int i = 0; i < nfiles; i++){
            if(files[i][0] != '\0'){
                total->lines = total->lines + ind_counts[i]->lines;
                total->words = total->words + ind_counts[i]->words;
                total->bytes = total->bytes + ind_counts[i]->bytes;
            }
        }
        print_stuff(total->lines, total->words, total->bytes, t_fname);
    }
    exit(EXIT_SUCCESS);
}

count_parts* count(int fd){
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
    if(!isspace(prev)){
        wcount = wcount +1;
    }
    count_parts *ret = (count_parts *) malloc(sizeof(count_parts));
    ret->lines = lcount;
    ret->words = wcount;
    ret->bytes = bcount;
    return ret;
}

count_parts* readCount(int status, int fd){
    count_parts *empty = (count_parts *) malloc(sizeof(count_parts));
    empty->lines = 0;
    empty->words = 0;
    empty->bytes = 0;

    count_parts *retval = empty;

    if(status != EXIT_SUCCESS){
        retval = empty;
    }
    else if(read(fd, retval, sizeof(count_parts)) != sizeof(count_parts)){
        retval = empty;
    }

    close(fd);
    return retval;
}

void print_stuff(long lines, long words, long bytes, char* fname){
    char *plines = (char *) malloc(sizeof(long));
    char *pwords = (char *) malloc(sizeof(long));
    char *pbytes = (char *) malloc(sizeof(long));
    int numFlags = 0;
    if(lines_flag){
        snprintf(plines, sizeof(long), "%li", lines);
        numFlags++;
    }
    if(words_flag){
        snprintf(pwords, sizeof(long), "%li", words);
        numFlags++;
    }
    if(bytes_flag){
        snprintf(pbytes, sizeof(long), "%li", bytes);
        numFlags++;
    }
    if(0 == numFlags){
        snprintf(plines, sizeof(long), "%li", lines);
        snprintf(pwords, sizeof(long), "%li", words);
        snprintf(pbytes, sizeof(long), "%li", bytes);
    }
    printf("     %s     %s     %s %s\n", plines, pwords, pbytes, fname);
}

void usage(){
    fprintf(stderr, "Usage: %s [-n <nproc>] [-wlc] [<files> ...]\n", progname);
    exit(EXIT_FAILURE);
}
