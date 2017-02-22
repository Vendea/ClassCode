#include<stdio.h>

/**
	Pointers in C are memory addresses of variables
	if x is a variable, &x is the pointer

	If p is a pointer, *p is the value at that location
*/
int main(){
	int x = 17, y = 18, z = 100;
	printf("%p\n", &x);
	printf("%p\n", &y);
	printf("%p\n", &z);

	int* px = &x;
	printf("%d\n", *px);
}