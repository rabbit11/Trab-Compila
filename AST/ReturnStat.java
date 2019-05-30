package AST;

//ReturnStat ::= "return" Expr ";"

public class ReturnStat extends Stat {
    // private VarDecStat var;
    private Expr expr;

    public ReturnStat(Expr e) {
        // this.var = v;
        this.expr = e;
    }

    @Override
    public void genC(PW pw) {
        pw.printBL(); // Quebra de linha

        pw.printI(); // Ident
        pw.printNI("return "); // Return

        // super.getExpr().genC(pw); // Expressão
        pw.printNI(";"); // ';'

        pw.printBL(); // Quebra de linha
    }
}
