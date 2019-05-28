package AST;

// AssignExprStat ::= Expr [ "=" Expr ] ";"

public class AssignExprStat extends Stat {
    public AssignExprStat(Variable var, Expr expr) {
        super(var, expr);

        // this.v = var;
        // this.exp = expr;
    }

    // private Variable v;
    // private Expr exp;

    @Override
    public void genC(PW pw) {
        pw.printBL(); // Quebra de linha

        pw.printI(); // Ident
        pw.printNI(super.getVar().getVar() + " = "); // Variavel
        super.getExpr().genC(pw); // Expressão
        pw.printNI(";"); // ';'

        pw.printBL(); // Quebra de linha
    }

    public void genCFOR(PW pw) {
        pw.printNI(super.getVar().getVar() + " = ");
        super.getExpr().genC(pw);
    }
}
