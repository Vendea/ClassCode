#include <stdio.h>

int main(int c, char *args[]){
	printf("c = %d\n", c);

	int i;
	for(i = 0; i < c; i++){
		printf("args[%d] = %s\n", i, args[i]);
	}
	printf("What is your name: ");
	char name[100];
	scanf("%s", name);
	printf("What is your favorite number: ");	
	int favNum;
	scanf("%d", &favNum);
	printf("%s's favorite number is %d\n", name, favNum);
	
}
