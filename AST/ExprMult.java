//     Nome                    RA
    // Bruno Asti Baradel      726499
    // Pablo Laranjo           726577
    // Pedro Coelho            743585
    // Vinícius Crepschi       743601

package AST;
import java.util.ArrayList;

import Lexer.*;

//ExprMult ::= ExprUnary {(” ∗ ” | ”/”)ExprUnary}

public class ExprMult extends Expr {
    ArrayList<ExprUnary> expr;
    private Type tipo;
    private Symbol op;

    public ExprMult(ArrayList<ExprUnary> expr, Symbol op, Type tipo) {    
        this.expr = expr;
        this.op = op;
        this.tipo = tipo;
    }

    @Override
    public void genC(PW pw) {
        int i = 0;
        for (ExprUnary p : expr) {
            p.genC(pw);
            i++;

            // if (op == Symbol.MULT && op == Symbol.DIV) {
                if (i > 0 && i < expr.size()) {
                    pw.print(" " + this.op.toString() + " ");
                }
            // }
        }
    }

    public void setExpr(ArrayList<ExprUnary> expr) {
        this.expr = expr;
    }

    public void setOp(Symbol op) {
        this.op = op;
    }

    public void setTipo(Type tipo) {
        this.tipo = tipo;
    }

    public ArrayList<ExprUnary> getExpr() {
        return this.expr;
    }

    public Symbol getOp() {
        return this.op;
    }

    public Type getType() {
        return this.tipo;
    }
}
