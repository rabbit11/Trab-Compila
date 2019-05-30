package AST;
import Lexer.*;

//ExprLiteral ::= LiteralInt | LiteralBoolean | LiteralString
public class ExprLiteral extends Expr {
    private Expr exp;
    private Symbol op;

    public ExprLiteral(Symbol s) {
        //this.setExp(exp);
      this.op = s;
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
