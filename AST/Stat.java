package AST;

//Stat ::= AssignExprStat | ReturnStat | VarDecStat | IfStat | WhileStat

abstract public class Stat {
  abstract public void genC(PW pw);
}

// import AST.VarDecStat;
// import AST.Expr;


// public class Stat {
//   private VarDecStat var;
//   private Expr expr;

//   public Stat(VarDecStat v, Expr e) {
//     this.var = v;
//     this.expr = e;
//   }

//   public void genC(PW pw) {
//   }

//   public VarDecStat getVar() {
//     return this.var;
//   }

//   public Expr getExpr() {
//     return this.expr;
//   }

//   public void setVar(VarDecStat var) {
//     this.var = var;
//   }

//   public void setExpr(Expr expr) {
//     this.expr = expr;
//   }
// }
