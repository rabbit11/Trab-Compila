package AST;

import java.util.ArrayList;

//FuncCall ::= Id "(" [ Expr {”, ”Expr} ] ")"

public class FuncCall extends Expr{
    private String funcName;
    private ArrayList<Expr> arrayExpr;

    public FuncCall(String s, ArrayList<Expr> e) {
        this.funcName = s;
        this.arrayExpr = e;
    }

    public void genC(PW pw) {
      // precisa fazer
    }

    public void setArrayExpr(ArrayList<Expr> e) {
        this.arrayExpr = e;
    }
}
