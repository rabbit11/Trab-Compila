//     Nome                    RA
    // Bruno Asti Baradel      726499
    // Pablo Laranjo           726577
    // Pedro Coelho            743585
    // Vinícius Crepschi       743601

package AST;

import java.util.ArrayList;

import Lexer.*;

//ExprAdd ::= ExprMult {(” + ” | ” − ”)ExprMult}

public class ExprAdd extends Expr{
    private Symbol op;
    private ArrayList<ExprMult> expr;
    private Type tipo;

    public ExprAdd(ArrayList<ExprMult> expr, Symbol op, Type tipo ){
        this.op = op;
        this.expr = expr;
        this.tipo = tipo;
    }
    
    @Override
    public void genC(PW pw){
        int i = 0;

        for (ExprMult p : expr) {
            p.genC(pw);
            i++;

            if(op != Symbol.PLUS && op != Symbol.MINUS){
                return;
            }
            
            else{
                if (i > 0 && i < expr.size()) {
                    pw.print(this.op.toString() + " ");
                }
            }

        }
    }

    public void setExpr(ArrayList<ExprMult> expr) {
        this.expr = expr;
    }

    public void setOp(Symbol op) {
        this.op = op;
    }

    public void setTipo(Type tipo) {
        this.tipo = tipo;
    }

    public ArrayList<ExprMult> getExpr() {
        return this.expr;
    }

    public Symbol getOp() {
        return this.op;
    }

    public Type getType() {
        return this.tipo;
    }
}
