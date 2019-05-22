package AST;

public class AssignExprStat extends Stat {
    public AssignExprStat(Variable var, Expr expr) {
        super(var, expr);
    }

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