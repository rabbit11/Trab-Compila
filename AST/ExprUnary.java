//     Nome                    RA
    // Bruno Asti Baradel      726499
    // Pablo Laranjo           726577
    // Pedro Coelho            743585
    // Vinícius Crepschi       743601

package AST;
import Lexer.*;
//ExprUnary ::= [ ( "+" | "-" ) ] ExprPrimary

public class ExprUnary extends Expr {
  private ExprPrimary expr;
  private Symbol op;
  private Type tipo;

  public ExprUnary(ExprPrimary expr, Symbol op, Type tipo){
    this.expr = expr;
    this.op = op;
    this.tipo = tipo;
  }
  public void genC(PW pw){
    // if(getOperador() != null)
    //   pw.printNI(operador.toString() + "");
  }

  public void setExpr(ExprPrimary expr) {
    this.expr = expr;
  }

  public void setOp(Symbol op) {
    this.op = op;
  }

  public void setTipo(Type tipo) {
    this.tipo = tipo;
  }

  public ExprPrimary getExpr() {
    return this.expr;
  }

  public Symbol getOp() {
    return this.op;
  }

  public Type getType() {
    return this.tipo;
  }
}
