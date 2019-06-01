//     Nome                    RA
    // Bruno Asti Baradel      726499
    // Pablo Laranjo           726577
    // Pedro Coelho            743585
    // Vinícius Crepschi       743601

package AST;
import java.util.ArrayList;
import AST.ParamDec;

//ParamList ::= ParamDec {”, ”ParamDec}

public class ParamList{
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
