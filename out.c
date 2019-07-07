#include <stdio.h>
#include <stdbool.h>
#include <string.h>


int constante () {

    int valorA;
    int valorB;
    valorA = 1 *  + 2 +  + 5 *  - 4;
    valorB = 10 / 2 - 20 /  - 5;
    if (false || valorB != 10) { 

        return valorA;
    }

    return valorB;
}

int multiplicar (int multA, int multB, bool tipo) {

    int total;
    if (tipo == true) { 
        int i;
        i = 1;
        total = 0;
        while (i <= multB){
            total = total + multA;
            i = i + 1;
        }

        return total;
    }
    if (tipo == false) { 
        total = multA * multB;

        return total;
    }
}

bool maiorIgual (int valorAa, int valorBb) {

    if (valorAa >= valorBb) { 

        return true;
    }
    else {

        return false;
    }
}

char mensagem (char msg) {

    printf("%s\n", msg);

    return "write";
}

void calc (bool oper) {

    int valor;
    int x;
    x =  - constante() + 100;
    valor = 1 + constante();
    if (oper == true || x > 80 && x < 0 && valor != 2 || valor > 50) { 
        valor = valor + 50;
    }
}

void mensagens (char msgA, char msgB) {

    char aux[100];
    strcpy(aux, "Ola, sua mensagem:");
    printf("%s", aux);
    printf("%s\n", msgA);
    printf("%s", aux);
    printf("%s\n", msgB);
}

bool portaAnd (bool eA, bool eB) {

    eA;
    eB;
    bool resultado;
    if (eA == true && eB == true) { 
        resultado = true;
    }
    else {
        resultado = false;
    }

    return resultado;
}

void comparador (char msgF, bool tipoComp) {

    char com[100];
    strcpy(com, "Permitido");
    if (tipoComp) { 
        strcpy(com, "Proibido");
    }
    if (    strcmp(msgF, com) == 0) { 
        printf("Acertou");
    }
}

void loopInfinito () {

    int expr;
    while (true){
    }
}

void main () {

    int resultadoM;
    char texto[100];
    resultadoM = multiplicar(constante(), 10, true);
    calc(maiorIgual(resultadoM, 10));
    strcpy(texto, mensagem("ola"));
    mensagens("Cuidado", "Pode ir");
    comparador("Permitido", portaAnd(true, true));
    loopInfinito();
}

