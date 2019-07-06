//     Nome                    RA
    // Bruno Asti Baradel      726499
    // Pablo Laranjo           726577
    // Pedro Coelho            743585
    // Vinícius Crepschi       743601

package AST;

import java.util.*;
import Lexer.*;
import AST.Type;

// VarDecStat ::= "var" Id ":" Type ";"

public class VarDecStat extends Stat{
  private String var;
  private Type tipo;

  public VarDecStat(String var, Type tipo) {
    this.var = var;
    this.tipo = tipo;
  }

  public void setVar(String var) {
    this.var = var;
  }

  public void setTipo(Type tipo) {
    this.tipo = tipo;
  }

  public void genC(PW pw) {
    if(this.tipo.getType() == Symbol.STRING){
      pw.println(this.tipo.tipo(pw) + " " + this.var + "[100];");
    }
    
    else{
      pw.println(this.tipo.tipo(pw) + " " + this.var + ";");
    }
  }

  public String getVar() {
    return this.var;
  }

  public Type getTipo() {
    return this.tipo;
  }
}
