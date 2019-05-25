package AST

//ExprUnary ::= [ ( "+" | "-" ) ] ExprPrimary

public class ExprUnary extends Expr{
  private Expr unaria;
  private Symbol operador;

  public ExprUnary(Expr unary, Symbol op){
    this.setUnaria(unary);
    this.setOperador(op);

  }

  public genC(PW pw){
    if(getOperador() != NULL)
      pw.printNI(operador.toString() + "");
  }

  public setUnaria(Expr unary)
    this.unaria = unary;

  public getUnaria()
    return this.unaria;

  public setOperador(Symbol op)
    this.operador = op;

  public getOperador()
    return this.operador;
}
