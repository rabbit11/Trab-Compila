package AST;

public class Variable extends Expr {

  public Variable(String v) {
    this.v = v;
  }

  public void genC(PW pw) {
    pw.out.print(v);
  }

  private String v;
}
