package AST;

import java.util.ArrayList;

//Program ::= Func {F unc}

public class Program {
    private ArrayList<Func> arrayFunc;

    public Program(ArrayList<Func> arrayFunc) {
        this.arrayFunc = arrayFunc;
    }

    public void genC(PW pw) {
        pw.println("#include <stdio.h>\n");

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
}
