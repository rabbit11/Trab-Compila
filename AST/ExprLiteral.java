package AST

//ExprLiteral ::= LiteralInt | LiteralBoolean | LiteralString
public class ExprLiteral extends Expr {
    private Expr exp;

    public ExprLiteral(Expr exp) {
        this.setExp(exp);
    }

    public void genC(PW pw) {
    }

    public void setExp(Expr exp) {
        this.exp = exp;
    }

    public Expr getExp() {
        return this.exp;
    }

}
