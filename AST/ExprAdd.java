//     Nome                    RA
// Bruno Asti Baradel      726499
// Pablo Laranjo           726577
// Pedro Coelho            743585
// Vinícius Crepschi       743601

package AST;

import java.util.ArrayList;

import Lexer.*;

//ExprAdd ::= ExprMult {(” + ” | ” − ”)ExprMult}

public class ExprAdd extends Expr {
    private ArrayList<Symbol> op;
    private ArrayList<ExprMult> expr;
    private Type tipo;

    public ExprAdd(ArrayList<ExprMult> expr, ArrayList<Symbol> op, Type tipo) {
        this.op = op;
        this.expr = expr;
        this.tipo = tipo;
    }

    @Override
    public void genC(PW pw) {
        int i = 0;
        int j = 0;
        int flag = 0;

        for (ExprMult p : expr) {
            if(this.tipo != null){

                if ((this.tipo.getType() == Symbol.STRING || 
                this.tipo.getType() == Symbol.STRINGLITERAL) && expr.size() > 1) {

                    if(i % 2 == 0){
                        flag = 1;
                        pw.print("strcat(");
                        p.genC(pw);
                    }
                    else if(i < expr.size()){
                        flag = 1;
                        pw.print(", ");
                        p.genC(pw);
                    }
                    else{
                        flag = 1;
                        p.genC(pw);
                    }
                    i++;
                }
            }
            if(flag == 0){
                p.genC(pw);
                i++;

                // if (i > 0 && i < expr.size()) {
                // pw.print(" " + this.op.toString() + " ");
                // }
                if (i > 0 && i < expr.size()) {
                    pw.print(" " + this.op.get(j).toString() + " ");
                    j++;
                }
            }
        }
    }

    public void setExpr(ArrayList<ExprMult> expr) {
        this.expr = expr;
    }

    public void setTipo(Type tipo) {
        this.tipo = tipo;
    }

    public void addOp(Symbol op) {
        this.op.add(op);
    }

    public ArrayList<ExprMult> getExpr() {
        return this.expr;
    }

    public ArrayList<Symbol> getOp() {
        return this.op;
    }

    public Type getType() {
        return this.tipo;
    }
}
