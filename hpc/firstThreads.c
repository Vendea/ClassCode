#include <pthread.h>
#include <stdio.h>
#include <math.h>
#include <stdlib.h>

void* counter(void* x);

pthread_mutex_t mut;

typedef struct tParams{
	int start;
	int end;
	pthread_t handle;
}tParams;

int main(int c, char *args[]){
	long N = 100000000;
	globalSum = 0;

	int numThreads = 8;
	pthread_t handle[numThreads];
	tParams params[numThreads];

	pthread_mutex_init(&mut, NULL);	
    for(int i = 0; i < numThreads; i++){
		params[i].start = (N/numThreads)*i;
		params[i].end = (N/numThreads) * (i+1)-1;
	
    	pthread_create(&(params[i].handle), NULL, &counter, (void*)(&params[i]));
    }
	
	double totalSum = 0;
	for(int i = 0; i < numThreads; i++){
		double *rv = 0;
		pthread_join(params[i].handle, &rv);
	}
    printf("The global sum is %d\n", totalSum);
}

void* counter(void* x){
	tParams* tp = (tParams*)x;
	
	double sum = 0;
    for(int i = tp->start; i <= tp->end; i++){
		double s = sqrt(2+sin(cos(sin(i))));
		sum += s;
    }
	void *pdouble = malloc(sizeof(double));
	*((double*)pdouble) = sum;
	return pdouble;
}
