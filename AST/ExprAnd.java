package AST;

//ExprAnd ::= ExprRel {”and”ExprRel}ExprAnd ::= ExprRel {”and”ExprRel}
public class ExprAnd extends Expr{

    private Expr esquerda, direita;
    private Symbol operador;

    public ExprAnd(Expr esq, Symbol op, Expr dir){
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
