//     Nome                    RA
    // Bruno Asti Baradel      726499
    // Pablo Laranjo           726577
    // Pedro Coelho            743585
    // Vinícius Crepschi       743601

package AST;
import Lexer.*;
//ExprUnary ::= [ ( "+" | "-" ) ] ExprPrimary

public class ExprUnary extends Expr {
  private Expr unaria;
  private Symbol operador;

  public ExprUnary(Expr unary, Symbol op){
    this.setUnaria(unary);
    this.setOperador(op);
  }

  public void genC(PW pw){
    if(getOperador() != null)
      pw.printNI(operador.toString() + "");
  }

  public void setUnaria(Expr unary) {
    this.unaria = unary;
  }

  public Expr getUnaria() {
    return this.unaria;
  }

  public void setOperador(Symbol op) {
    this.operador = op;
  }

  public Symbol getOperador() {
    return this.operador;
  }
}
