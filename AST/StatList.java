//     Nome                    RA
    // Bruno Asti Baradel      726499
    // Pablo Laranjo           726577
    // Pedro Coelho            743585
    // Vinícius Crepschi       743601

package AST;

import java.util.*;

//StatList ::= "{” {Stat} ”}"

public class StatList{
    private ArrayList<Stat> listaStats;

    public StatList(ArrayList<Stat> listaStats){
      this.listaStats = listaStats;
    }

    public void genC(PW pw){
        for(Stat s : listaStats){
          s.genC(pw);
        }
    }

    public ArrayList<Stat> getList(){
      return this.listaStats;
    }

    public void setList(ArrayList<Stat> listaStats){
      this.listaStats = listaStats;
    }
}
