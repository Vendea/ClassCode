#include <pthread.h>
#include <stdio.h>

int main(int c, char *args[]){
	int N = 10;
	pthread_t handle;
	
	pthread_create(&handle, 0, &counter, (void*)N);
}
