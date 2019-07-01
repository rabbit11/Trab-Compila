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
    private int valueInt;

    public ExprLiteral(String value, Symbol op, Type tipo) {
        this.op = op;
        this.value = value;
        this.tipo = tipo;
        this.valueInt = -1337;
    }

    public ExprLiteral(int value, Symbol op, Type tipo) {
        this.op = op;
        this.value = null;
        this.tipo = tipo;
        this.valueInt = value;
    }

    @Override
    public void genC(PW pw) {
        if(this.valueInt != -1337){
            pw.print(Integer.toString(valueInt));
        }
        else{
            if(tipo.getType() == Symbol.STRINGLITERAL){
                value = value.substring(0, value.length() - 1);
            }
            pw.print(value);
        }
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

    public int getValueInt(){
        return this.valueInt;
    }

    public Type getType(){
        return this.tipo;
    }

}
