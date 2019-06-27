//     Nome                    RA
    // Bruno Asti Baradel      726499
    // Pablo Laranjo           726577
    // Pedro Coelho            743585
    // Vinícius Crepschi       743601

package AST;
import Lexer.*;
//ExprUnary ::= [ ( "+" | "-" ) ] ExprPrimary

public class ExprUnary extends Expr {
  private Expr expr;
  private Symbol op;
  private Type tipo;

  public ExprUnary(Expr expr, Symbol op, Type tipo){
    this.expr = expr;
    this.op = op;
    this.tipo = tipo;
  }
  public void genC(PW pw){

    if(this.op == Symbol.PLUS || this.op == Symbol.MINUS){
      pw.print(this.op.toString() + " ");
    }

    expr.genC(pw);
  }

  public void setExpr(Expr expr) {
    this.expr = expr;
  }

  public void setOp(Symbol op) {
    this.op = op;
  }

  public void setTipo(Type tipo) {
    this.tipo = tipo;
  }

  public Expr getExpr() {
    return this.expr;
  }

  public Symbol getOp() {
    return this.op;
  }

  public Type getType() {
    return this.tipo;
  }
}
