package AST;

import java.util.ArrayList;

//Program ::= Func {Func}

public class Program {
    private ArrayList<Func> arrayFunc;
    private StatList statList;

    public Program(ArrayList<Func> arrayFunc, StatList statList) {
        this.arrayFunc = arrayFunc;
        this.statList = statList;
    }

    public void genC(PW pw) {
        pw.out.println("#include <stdio.h>");
        pw.out.println();
        pw.println("void main() {");
        pw.add();
        // generate code for the declaration of variables
        for (VarDecStat var : arrayVariable){
            pw.println(var.getType().getCname() + " " + var.getName() + ";");
        }
        pw.out.println("");
        statList.genC(pw);
        pw.sub();
        pw.out.println("}");
    }
}

// public class Program {
//     private ArrayList<Func> arrayFunc;

//     public Program(ArrayList<Func> arrayFunc) {
//         this.arrayFunc = arrayFunc;
//     }

//     public void genC(PW pw) {
//         pw.println("#include <stdio.h>\n");

//         // Se não tiver nenhuma função declarada
//         if (arrayFunc.contains(null)) {
//             pw.println("int main(){}");
//         }

//         // Pode ter funções declaradas
//         else {
//             for (Func f : arrayFunc) {
//                 f.genC(pw);
//             }
//         }
//     }
// }
