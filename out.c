#include <stdio.h>
#include <stdbool.h>
#include <string.h>


int fatorial (int n) {

    if (n <= 0) { 

        return 1;
    }
    else {

        return n * fatorial(n - 1);
    }
}

void imprimir (int before, int valor, char* after) {

    int i;
    i = 0;
    while (i < before){
        printf("*");
        i = i + 1;
    }
    printf("\n");
    printf("%d\n", valor);
    printf("%s\n", after);
}

void main () {

    imprimir(50, fatorial(5) * 2 * fatorial(3), " 	fim");
}

