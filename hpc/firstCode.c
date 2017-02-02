#include <stdio.h>

int main(){
	//arrayPointers();
	int N;
	int* pN = &N;
	printf("%p\n%p\n", pN, pN+1);
	printf("A pointer has size %ld\n", sizeof(pN));
}

