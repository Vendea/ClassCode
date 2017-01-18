#include <stdio.h>
#include <stdlib.h>

int fib();

main(){
	char str[100];
	while( 1 ){
		printf("Enter a number: ");
		scanf("%s", str);
		int v = atoi(str);
		printf("Fib #%d is %d.\n", v, fib(v));
	}
}

int
fib(int n){
	if (n == 0) {
		return 0;
	}
	if (n == 1) {
		return 1;
	}

	int fi = 1, fim1 = 0;
	for ( int i = 0; i < n-1; i++ ){
		int fip1 = fi + fim1;
		fim1 = fi;
		fi = fip1;
	}
	return fi;
}
