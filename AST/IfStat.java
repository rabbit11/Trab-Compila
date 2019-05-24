package AST;

import java.util.ArrayList;

public class IfStat extends Stat {
    private ArrayList<Stat> arrayStmt;

    public IfStat(Var var, Expr expr) {
        super(var, expr);
        this.arrayStmt = stmt;
    }

    @Override
    public void genC(PW pw) {
        pw.printBL(); // Quebra de linha

        // Cabeçalho do IF
        pw.printI(); // Ident
        pw.printNI("if ("); // Variavel
        super.getExpr().genC(pw); // Expressão
        pw.printNI(")");
        pw.printBL();

        // corpo do IF
        pw.print("{");
        pw.add();
        // corpo.genC(pw);
        for (Statement s : arrayStmt) {
            s.genC(pw);
        }

        pw.sub();
        pw.println("}");

        // Else
        // corpoElse.genC(pw);
    }

    public void setCorpo(IfBody corpo) {
        this.corpo = corpo;
    }

    // public void setCorpoElse(ElseStat corpo) {
    //     this.corpoElse = corpo;
    // }

    public void setArrayVar(ArrayList<Statement> stmt) {
        this.arrayStmt = stmt;
    }

    public IfBody getCorpo() {
        return this.corpo;
    }

    public ElseStat getCorpoElse() {
        return this.corpoElse;
    }
}