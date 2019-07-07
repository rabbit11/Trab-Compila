#include <stdio.h>
#include <stdbool.h>
#include <string.h>


int recebeDado () {

    int num;
    int numDois;
    char texto[100];
    scanf("%d", &num);
    scanf("%d", &numDois);
    scanf("%s", texto);
    printf("%s\n", texto);

    return num * numDois;
}

void main () {

    int result;
    bool logico;
    logico = true;
    while (logico){
        result = recebeDado();
        printf("%d\n", result);
        if (result == 0) { 
            logico = false;
        }
    }
    printf("teste sem erro\n");
}

