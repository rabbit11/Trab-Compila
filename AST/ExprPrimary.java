package AST;

//ExprPrimary ::= Id | FuncCall | ExprLiteral

public class ExprPrimary extends Expr{
  private VarDecStat var;
  private Expr expr;

  public ExprPrimary(VarDecStat v, Expr e) {
    this.var = v;
    this.expr = e;
  }

  public void genC(PW pw) {
  }
}
