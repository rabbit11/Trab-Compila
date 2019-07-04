#include <stdio.h>
#include <stdbool.h>

//inicialização de variáveis necessárias na leitura de dados da entrada:
int readInt = 0;
char readString[100] = "a";

void imprime (int q, int w, int e, Char[100] nomeFunc) {
 
printf("**************\n");
printf("%d\n", q);
printf("%d\n", w);
printf("%d\n", e);
printf("%s\n", nomeFunc);
printf("***Fim***\n");
 
}
int aninhamento (int k, int l) {
 
int i;
int j;
bool alternaLogico;
bool a;
int b;
Char[100] eleven;
int morma;
int final;
final = 0;
b = 4000;
i = 0;
j = k * l;
a = false;
i = j + b - final;
eleven = scanf("%s", &readString);
morma = scanf("%d", &readInt);
while ( i <= k ){
alternaLogico = true;
while ( j >= l ){
if ( a == alternaLogico ) { 
b = b / 2;
final = final + 1;
}
else {
if ( b < 2 || b != 4 && b > 5 ) { 
b = b - 1;
}
}
alternaLogico = false;
j = j - 2;
final = final - 1;

}
i = i + 1;

}
imprime(i, j, b, " aninhamento");

return final;
 
}
void main () {
 
int h;
int g;
int res;
res = aninhamento(h, g);
Char[100] n;
n = "variavel de texto da main";
imprime(90, 30, 30, "chamada de funcao da main");
int i;
i = 0;
int resDois;
while ( i != 3 ){
imprime(i, i + 1, i + 2, "i, i+1, i+2");
i = i + 1;
if ( i == 2 ) { 
resDois = aninhamento(i, i + 1);
}

}
 
}
