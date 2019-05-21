package AST;

import AST.VarDecStat;
import AST.CompositeExpr;

public class Stat {
  private VarDecStat var;
  private CompositeExpr expr;

  public Stat(VarDecStat v, CompositeExpr e) {
    this.var = v;
    this.expr = e;
  }

  public void genC(PW pw) {
    // faz nada
  }

  public VarDecStat getVar() {
    return this.var;
  }

  public CompositeExpr getExpr() {
    return this.expr;
  }

  public void setVar(VarDecStat var) {
    this.var = var;
  }

  public void setExpr(CompositeExpr expr) {
    this.expr = expr;
  }
}