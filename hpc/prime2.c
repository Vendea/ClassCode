/***
 * Counts the number of primes in the range 1..(N-1)
 * N is read from the command line.
 *
 * countPrimes.c --- Threaded. The number of threads is specified in main.
 * The for loop in main calls the "oneThread" function, launched
 *   in its own thread. We intend to have lots of threads, so we use a
 *   runningThreads semaphore to restrict the number that are running at
 *   any given time.
 *
 * The "count" variable is declared globally, but each thread increments
 *   it only once. Each thread keeps a local "myCount" variable which it
 *   increments for each int, and only when it is finished, does it add
 *   its local myCount to the global "count" variable.
 *
 * We also use a mutex to protect access to the shared "count" variable.
 *
 *
 * compile: gcc -o cp1 countPrimes1.c -lpthread
 *     run: ./cp1 1000 8
 *
 * Up to 1000, 8 threads
 **/

#include<stdio.h>
#include<stdlib.h>
#include<math.h>
#include<omp.h>

// Function prototypes
int isPrime(int k);

// Global variables, shared by all threads
int count;

int main(int argc, char *argv[]){
  long N = atoi(argv[1]);
  int numThreads = atoi(argv[2]);
  count = 0;
  /**#pragma omp parallel num_threads(numThreads) schedule(dynamic){
     for(int i = 2, i < N, i++){
     if(isPrime(i)){
     #pragma omp critical
     count++;
     }
     }**/
     int innercount = 0;
#pragma omp parallel for num_threads(numThreads)\
  reduction(+:count) schedule(dynamic, 1000)
  for(int i = 2; i < N; i++){
    if(isPrime(i)){
      count++;
    }
  }
 
  printf("There are %d primes up to %ld\n", count, N);

return 0;
}

/*
 * Returns true if the number is a prime >= 2
 * and false otherwise.
 *
 * In C, "0" means "false, "not 0" means "true".
 */
int isPrime(int p){
  if(p == 2)
    return 1;
  if(p < 2 || (p % 2) == 0)
    return 0;

  int i;            /* counter */
  for(i = 3; i <= sqrt(p); i = i + 2){
    if(p % i == 0)
      return 0;
  }

  return 1;            /* If we make it here, N has no divisors */
}