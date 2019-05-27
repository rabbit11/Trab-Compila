package AST;
import java.util.ArrayList;

//ParamList ::= ParamDec {”, ”ParamDec}

public class ParamList extends ParamDec {
    private ArrayList<ParamDec> arrayParam;

    public ParamList(ArrayList<ParamDec> p) {
        this.arrayParam = p;
    }

    public void genC(PW pw) {
        for (ParamDec p : arrayParam) {
            p.genC(pw);
        }
    }

    public void setArrayParam(ArrayList<ParamDec> p) {
        this.arrayParam = p;
    }
}

