//     Nome                    RA
    // Bruno Asti Baradel      726499
    // Pablo Laranjo           726577
    // Pedro Coelho            743585
    // Vinícius Crepschi       743601

package AST;
import java.util.ArrayList;

import Lexer.*;

//ExprRel ::= ExprAdd [ RelOp ExprAdd ]

public class ExprRel extends Expr {

    private ArrayList<Expr> expr;
    private Symbol op;
    private Type tipo;

    public ExprRel(ArrayList<Expr> expr, Symbol op, Type tipo){
        this.expr = expr;
        this.op = op;
        this.tipo = tipo;
    }

    public void genC(PW pw){
        // esquerda.genC(pw);

        // if(this.operador != null){
        //     pw.printNI(" " + operador.toString() + " ");
        // }

        // if (this.direita != null) {
        //     direita.genC(pw);
        // }
    }

    public void setOp(Symbol op) {
        this.op = op;
    }

    public void setExpr(ArrayList<Expr> expr){
        this.expr = expr;
    }

    public void setType(Type tipo){
        this.tipo = tipo;
    }

    public ArrayList<Expr> getExpr(){
        return this.expr;
    }

    public Type getType(){
        return this.tipo;
    }
    
    public Symbol getOperador() {
        return this.op;
    }
}
