//     Nome                    RA
// Bruno Asti Baradel      726499
// Pablo Laranjo           726577
// Pedro Coelho            743585
// Vinícius Crepschi       743601

package AST;

import java.util.ArrayList;

import Lexer.*;

//Expr ::= ExprAnd { "or" ExprAnd}
public class ExprOr extends Expr {

    private ArrayList<Expr> expr;
    private Symbol operador;
    private Type tipo;

    public ExprOr(ArrayList<Expr> expr, Symbol op, Type tipo) {
        this.expr = expr;
        this.operador = op;
        this.tipo = tipo;
    }

    @Override
    public void genC(PW pw) {
        int i = 0;

        for (Expr p : expr) {
            p.genC(pw);
            i++;

            // if(this.operador == Symbol.OR){
                if (i > 0 && i < expr.size()) {
                    // pw.print(" " + this.operador.toString() + " ");
                    pw.print(" " + "|| ");
                }
            // }
        }

    }

    public void setExpr(ArrayList<Expr> expr) {
        this.expr = expr;
    }

    public void setOp(Symbol op) {
        this.operador = op;
    }

    public void setTipo(Type tipo) {
        this.tipo = tipo;
    }

    public ArrayList<Expr> getExpr() {
        return this.expr;
    }

    public Symbol getOp() {
        return this.operador;
    }

    public Type getType() {
        return this.tipo;
    }

}
