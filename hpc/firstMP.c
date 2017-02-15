#include<stdio.h>
#include<omp.h>
#include<stdlib.h>

int count;
int getSum(int x);

int main(int c, char*argv[]){
  int N = atoi(argv[1]);
  
  count = 0;

#pragma omp parallel num_threads(N)

  {
    int x = omp_get_thread_num();
    int mySum = getSum(x+1);
#pragma omp critical
    count += mySum;
  }
  
  printf("Count = %d \n", count);

}

int getSum(int x){
  int rv = 0;
  for(int i = 1; i < x+1; i++){
    rv += i;
  }
  return rv;
}
