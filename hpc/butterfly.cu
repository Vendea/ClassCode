#include<stdio.h>
#include <cuda.h>

// Forward Declarations
#define BLOCKSIZE 1024

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

   int N = 1024;
   a = (int*)malloc(N * sizeof(int));

  // Fill the array
  int i;			/* counter */
  for(i = 0; i < N; i++)
    a[i] = rand() % 23;

  printArray(20);

  // Allocate space on the GPU
  int* d_Array;			/* d_ means "device" */
  int* d_answer;
  err = cudaMalloc(&d_Array, N * sizeof(int));
  printf("Malloc device rules: %s\n",cudaGetErrorString(err));
  err = cudaMalloc(&d_answer, sizeof(long));
  printf("Malloc device rules: %s\n",cudaGetErrorString(err));


  // Copy the array to the card
  // destination, then source
  err = cudaMemcpy(d_Array, a, N * sizeof(int), cudaMemcpyHostToDevice);
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
  err = cudaMemcpy(a, d_Array, N*sizeof(int), cudaMemcpyDeviceToHost);
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
  __shared__ int a[BLOCKSIZE];

  a[threadIdx.x] = d_a[threadIdx.x];
  __syncthreads();

  for (int i = 0; i < log2f(blockDim.x); i++){
    int idx = threadIdx.x;
    int neighbor = idx ^ (1<<i);
    int his = a[neighbor];
    int my = a[idx];
    int holder = my + his;
    __syncthreads();
    a[idx] = holder;
    __syncthreads();
  }
  *d_answer = a[threadIdx.x];
}
