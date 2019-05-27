package AST;

import java.util.*

//StatList ::= "{” {Stat} ”}"

public class Statlist extends Stat{
    private Arraylist<Stat> listaStats;


    public Statlist(Arraylist listaStats){
      this.setListaStats(listaStats);

    }

    public genC(PW pw){
      pw.printNI("{");
      for(int i = 0; i < listaStats.size(); i++)
        pw.print(listaStats[i]); //não sei qual print usar aqui
      pw.printNI("}");
    }


    public setListaStats(Stat a)
      this.listaStats = a;


    public getListaStats()
      return this.listaStats;

}
