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

    public void genC(PW pw) {
      // precisa fazer
    }

    public void setArrayExpr(ArrayList<Expr> e) {
        this.arrayExpr = e;
    }

    public Type getType(){
        return this.tipo;
    }
}
