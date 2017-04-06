#include<stdio.h>
#include <time.h> 
#include <cuda.h>

// Forward Declarations
#define BLOCKSIZE 1024
#ifndef Nsize
#define Nsize 1024
#endif

void printArray(int k);
__global__ void add(int d_a[], int *d_answer);

int* a;
int answer;

 int main(){
   cudaError_t err;

   int deviceCount;
   err = cudaGetDeviceCount(&deviceCount);
   printf("Device count: %s\n",cudaGetErrorString(err));
   printf("There are %d devices\n", deviceCount);

   err = cudaSetDevice(0);
   printf("Device selection: %s\n",cudaGetErrorString(err));

   a = (int*)malloc(Nsize * sizeof(int));

  // Fill the array
  int i;			/* counter */
  time_t t;
  //srand((unsigned) time(&t));
  for(i = 0; i < Nsize; i++)
    a[i] = rand() % 23;

  printArray(Nsize);

  // Allocate space on the GPU
  int* d_Array;			/* d_ means "device" */
  int* d_answer;
  err = cudaMalloc(&d_Array, Nsize * sizeof(int));
  printf("Malloc device rules: %s\n",cudaGetErrorString(err));
  err = cudaMalloc(&d_answer, sizeof(long));
  printf("Malloc device rules: %s\n",cudaGetErrorString(err));


  // Copy the array to the card
  // destination, then source
  err = cudaMemcpy(d_Array, a, Nsize * sizeof(int), cudaMemcpyHostToDevice);
  printf("cuda memory error: %s\n",cudaGetErrorString(err));
  err = cudaMemcpy(d_answer, &answer, sizeof(int), cudaMemcpyHostToDevice);
  printf("cuda memory error: %s\n",cudaGetErrorString(err));

  // Set up the kernel
  int blockSize = BLOCKSIZE;
  int numBlocks = 1;
  dim3 dimGrid(numBlocks);
  dim3 dimBlock(blockSize);

  // Launch the kernel
  add <<< dimGrid, dimBlock >>> (d_Array, d_answer);

  // Retrieve the results from the card
  err = cudaMemcpy(&answer, d_answer, sizeof(int), cudaMemcpyDeviceToHost);
  printf("cuda memory error: %s\n",cudaGetErrorString(err));
  err = cudaMemcpy(a, d_Array, Nsize*sizeof(int), cudaMemcpyDeviceToHost);
  printf("cuda memory error: %s\n",cudaGetErrorString(err));

  // Inspect the results.
  printf("%i\n", answer);
  printArray(20);
}

void printArray(int k){
  int i;
  for(i = 0; i < k; i++)
    printf("%d ", a[i]);
  printf("\n");
}


__global__ void add(int d_a[], int *d_answer){
  int idx = threadIdx.x;
  if(idx >= Nsize){
    return;
  }
  __shared__ int a[BLOCKSIZE];

  a[idx] = d_a[idx];
  __syncthreads();

  for (int i = 0; i < (log2f(BLOCKSIZE)); i++){
    int neighbor = idx ^ (1<<i);
    int his = 0;
    if(neighbor >= Nsize){
      his = 0;
    }
    else{
      his = a[neighbor];
    }
    int my = a[idx];
    int holder = my + his;
    __syncthreads();
    a[idx] = holder;
    __syncthreads();
  }
  *d_answer = a[idx];
}
