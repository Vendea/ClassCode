#include<stdio.h>
#include<omp.h>
#include<stdlib.h>

long count;
long getSum(int x);

int main(int c, char*argv[]){
  int N = atoi(argv[1]);
  int numThreads = atoi(argv[2]); 
  count = 0;

#pragma omp parallel for num_threads(numThreads) schedule(dynamic, 100)
  for(int i = 1; i <=N; i++){
    long mySum = getSum(i);
#pragma omp critical
    count += mySum;
  }
  
  printf("Count = %ld \n", count);

}

long getSum(int x){
  long rv = 0;
  for(int i = 0; i < x; i++){
    rv += i;
  }
  return rv;
}
