package AST;

import java.util.ArrayList;

public class FuncCall {
    private ArrayList<VarDecStat> arrayVar;
    private ArrayList<Stat> arrayStat;

    public FuncCall(ArrayList<VarDecStat> var, ArrayList<Stat> stmt) {
        this.arrayVar = var;
        this.arrayStat = stmt;
    }

    public void genC(PW pw) {
        for (VarDecStat v : arrayVar) {
            v.genC(pw);
        }
        for (Stat s : arrayStat) {
            s.genC(pw);
        }
    }

    public void setArrayVar(ArrayList<VarDecStat> var) {
        this.arrayVar = var;
    }
}