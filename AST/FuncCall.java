//     Nome                    RA
    // Bruno Asti Baradel      726499
    // Pablo Laranjo           726577
    // Pedro Coelho            743585
    // Vinícius Crepschi       743601

package AST;

import java.util.ArrayList;

//FuncCall ::= Id "(" [ Expr {”, ”Expr} ] ")"

public class FuncCall extends Expr{
    private String funcName;
    private ArrayList<Expr> arrayExpr;
    Type tipo;

    public FuncCall(String s, ArrayList<Expr> e, Type tipo) {
        this.funcName = s;
        this.arrayExpr = e;
        this.tipo = tipo;
    }

    public FuncCall(String s, ArrayList<Expr> e) {
        this.funcName = s;
        this.arrayExpr = e;
        this.tipo = null;
    }

    public FuncCall(String s, Type tipo) {
        this.funcName = s;
        this.arrayExpr = null;
        this.tipo = tipo;
    }

    public FuncCall(String s) {
        this.funcName = s;
        this.arrayExpr = null;
        this.tipo = null;
    }

    public void genC(PW pw) {
      pw.print(this.funcName);

      pw.print("(");

      if(this.arrayExpr != null){
        int i = 0;
        
        for (Expr p : arrayExpr) {
            p.genC(pw);
            i++;

            if (i > 0 && i < arrayExpr.size()) {// garante que imprimimos , apenas quando tem mais de 1 param
                pw.printNI(", "); // e não imprimimos após o último
            }
        }
        pw.print(")");
      }
    }

    public void setArrayExpr(ArrayList<Expr> e) {
        this.arrayExpr = e;
    }

    public Type getType(){
        return this.tipo;
    }
}
