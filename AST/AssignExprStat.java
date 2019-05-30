package AST;

// AssignExprStat ::= Expr [ "=" Expr ] ";"

public class AssignExprStat extends Stat {
    private Expr esq, dir;

    public AssignExprStat(Expr esq, Expr dir) {
        this.esq = esq;
        this.dir = dir;
    }

    @Override
    public void genC(PW pw){}
    // @Override
    // public void genC(PW pw) {
    //     pw.printBL(); // Quebra de linha

    //     pw.printI(); // Ident
    //     pw.printNI(super.getVar().getVar() + " = "); // Variavel
    //     super.getExpr().genC(pw); // Expressão
    //     pw.printNI(";"); // ';'

    //     pw.printBL(); // Quebra de linha
    // }

    // public void genCFOR(PW pw) {
    //     pw.printNI(super.getVar().getVar() + " = ");
    //     super.getExpr().genC(pw);
    // }
}
