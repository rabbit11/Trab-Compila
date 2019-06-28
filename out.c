#include <stdio.h>
#include <stdbool.h>

//inicialização de variáveis necessárias na leitura de dados da entrada:
int readInt = 0;
char readString[100] = "a";

int soma (int x, int y) {
 
int a;
a = x + y;

return a;
 
}
void main () {
 
int result;
result = soma(3, 2);
result = scanf("%d" , &readInt);
printf("%d\n", result);
printf("%d", result);
 
}
