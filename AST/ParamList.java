package AST;
import java.util.ArrayList;
import AST.ParamDec;

//ParamList ::= ParamDec {”, ”ParamDec}

public class ParamList extends ParamDec {
    private ArrayList<ParamDec> arrayParam;

    public ParamList(ArrayList<ParamDec> p) {
        this.arrayParam = p;
    }

    public void genC(PW pw) {
        int i = 0;

        for (ParamDec p : arrayParam) {
            p.genC(pw);
            i++;
        }

        if(i > 0 && i < arrayParam.size()){//garante que imprimimos , apenas quando tem mais de 1 param
            pw.printNI(",");               //e não imprimimos após o último
        }
    }

    public void setArrayParam(ArrayList<ParamDec> p) {
        this.arrayParam = p;
    }

    public ArrayList<ParamDec> getListaStats(){
      return this.arrayParam;
    }
    
}