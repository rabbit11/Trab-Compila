//     Nome                    RA
    // Bruno Asti Baradel      726499
    // Pablo Laranjo           726577
    // Pedro Coelho            743585
    // Vinícius Crepschi       743601

package AST;
import Lexer.*;

//ExprLiteral ::= LiteralInt | LiteralBoolean | LiteralString
public class ExprLiteral extends Expr {
    private String value;
    private Expr exp;
    private Symbol op;
    private Type tipo;

    public ExprLiteral(String value, Symbol op, Type tipo) {
      this.op = op;
      this.value = value;
      this.tipo = tipo;
    }

    public void genC(PW pw) {
        pw.print(value);
    }

    public void setExp(Expr exp) {
        this.exp = exp;
    }

    public Expr getExp() {
        return this.exp;
    }

    public String getValue(){
        return this.value;
    }

    public Type getType(){
        return this.tipo;
    }

}
