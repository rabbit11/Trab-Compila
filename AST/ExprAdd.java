//     Nome                    RA
    // Bruno Asti Baradel      726499
    // Pablo Laranjo           726577
    // Pedro Coelho            743585
    // Vinícius Crepschi       743601

package AST;

import Lexer.*;

//ExprAdd ::= ExprMult {(” + ” | ” − ”)ExprMult}

public class ExprAdd extends Expr{
    private Expr esquerda, direita;
    private Symbol operador;

    public ExprAdd(Expr esq, Symbol op, Expr dir){
        this.setDireita(dir);
        this.setEsquerda(esq);
        this.setOperador(op);
    }

    public void genC(PW pw){
        esquerda.genC(pw);

        if(this.operador != null){
            pw.printNI(" " + operador.toString() + " ");
        }

        if (this.direita != null) {
            direita.genC(pw);
        }
    }

    public void setEsquerda(Expr esq) {
        this.esquerda = esq;
    }

    public void setDireita(Expr dir) {
        this.direita = dir;
    }

    public void setOperador(Symbol op) {
        this.operador = op;
    }

    public Expr getEsquerda() {
        return this.esquerda;
    }

    public Expr getDireita() {
        return this.direita;
    }

    public Symbol getOperador() {
        return this.operador;
    }
}
