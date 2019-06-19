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
    pw.println("var" + this.var + ":" + this.tipo.getType() + ";");
  }

  public String getVar() {
    return this.var;
  }

  public Symbol getTipo() {
    return this.tipo.getType();
  }
}
