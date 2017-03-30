#include<stdio.h>
#include <cuda.h>

// Forward Declarations
void printArray(int k);
__global__ void dotprod(double d_a[], double d_b[]);

double *a, *b;
double *result;

int main(){
  cudaError_t err;

  int deviceCount;
  err = cudaGetDeviceCount(&deviceCount);
  printf("Device count: %s\n",cudaGetErrorString(err));
  printf("There are %d devices\n", deviceCount);

  err = cudaSetDevice(0);
  printf("Device selection: %s\n",cudaGetErrorString(err));

  int N = 100000000;
  // Space on the host for two double arrays
  a = (double*)malloc(N * sizeof(double));
  b = (double*)malloc(N * sizeof(double));
  

  // Fill the arrays
  int i;            /* counter */
  for(i = 0; i < N; i++){
    a[i] = ((double)rand())/rand();
    b[i] = ((double)rand())/rand();
  }
  printArray(20);

  // Allocate space on the GPU
  double* d_Array_a;
  double* d_Array_b;
  double* d_result; 
  err = cudaMalloc(&d_Array_a, N * sizeof(double));
  printf("Malloc device rules: %s\n",cudaGetErrorString(err));
  err = cudaMalloc(&d_Array_b, N * sizeof(double));
  printf("Malloc device rules: %s\n",cudaGetErrorString(err));
  err = cudaMalloc(&d_result, sizeof(double));
  printf("Malloc device rules: %s\n",cudaGetErrorString(err));

  // Copy the aray to the card
  // destination, then source
  err = cudaMemcpy(d_Array_a, a, N*sizeof(double), cudaMemcpyHostToDevice);
  printf("Memory copy error: %s\n", cudaGetErrorString(err));
  err = cudaMemcpy(d_Array_b, b, N*sizeof(double), cudaMemcpyHostToDevice);
  printf("Memory copy error: %s\n", cudaGetErrorString(err));
  err = cudaMemcpy(d_result, result, sizeof(double), cudaMemcpyHostToDevice);
  printf("Memory copy error: %s\n", cudaGetErrorString(err));

  // Set up the kernel
  int blockSize = 512;
  int numBlocks = (N + blockSize - 1)/blockSize;
  dim3 dimGrid(numBlocks);
  dim3 dimBlock(blockSize);

  // Launch the kernel
  dotprod <<< dimGrid, dimBlock >>> (d_Array_a, d_Array_b, result);

  // Retrieve the results from the card
  err = cudaMemcpy(result, d_result, N*sizeof(double), cudaMemcpyDeviceToHost);
  printf("Memory copy error: %s\n", cudaGetErrorString(err));

  // Inspect the results.
  printf("dot product result: %s\n", result);

}

void printArray(int k){
  int i;
  for(i = 0; i < k; i++)
    printf("%d ", a[i]);
  printf("\n");
}

__global__ void dotprod(double d_a[], double d_b[], double r[]){
  int idx = blockIdx.x * blockDim.x + threadIdx.x;
  double val = d_a[idx] * d_b[idx];
  atomicAdd(&(r[0]), val);
}
