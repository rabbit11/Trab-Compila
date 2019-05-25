package AST;

import java.util.ArrayList;

//WhileStat ::= "while" Expr StatList

public class WhileStat extends Stat {
    // super expr sera as condições do while

    // private WhileBody corpo;
    private ArrayList<AssignExprStat> atribuicaoInicio;
    private ArrayList<AssignExprStat> atribuicaoFinal;

    public WhileStat(VarDecStat var, Expr expr) {
        super(var, expr);
    }

    @Override
    public void genC(PW pw) {
        pw.printBL(); // Quebra de linha

        // ----- Cabeçalho do while ------
        pw.printI(); // Ident
        pw.printNI("while ("); // inicio

        // Condição do while
        super.getExpr().genC(pw);

        pw.printNI(")");
        pw.printBL();
        // -----------------------------

        // corpo do while
        pw.print("{");
        pw.add();
        corpo.genC(pw);
        pw.sub();
        pw.println("}");
    }

    public void setCorpo(whileBody corpo) {
        this.corpo = corpo;
    }

    public whileBody getCorpo() {
        return this.corpo;
    }
}
