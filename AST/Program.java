//     Nome                    RA
    // Bruno Asti Baradel      726499
    // Pablo Laranjo           726577
    // Pedro Coelho            743585
    // Vinícius Crepschi       743601

package AST;

import java.util.ArrayList;

//Program ::= Func {Func}

public class Program {
    private ArrayList<Func> arrayFunc;

    public Program(ArrayList<Func> arrayFunc) {
        this.arrayFunc = arrayFunc;
    }

    public void genC(PW pw) {
        pw.println("#include <stdio.h>");
        pw.println("#include <stdbool.h>");
        pw.printBL();

        pw.println("//inicialização de variáveis necessárias na leitura de dados da entrada:");
        pw.println("int readInt = 0;");
        pw.println("char readString[100] = \"a\";");

        pw.printBL();
        // Se não tiver nenhuma função declarada
        if (arrayFunc.contains(null)) {
            pw.println("int main(){}");
        }

        // Pode ter funções declaradas
        else {
            for (Func f : arrayFunc) {
                f.genC(pw);
            }
        }
    }

    public ArrayList<Func> getArrayFunc(){
        return this.arrayFunc;
    }
}
