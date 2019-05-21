// VarDecStat ::= "var" Id ":" Type ";"

package AST;

import java.util.*;
import Lexer.*;

public class Variable {
  private String var;
  private Symbol tipo;

  public Variable(String var, Symbol tipo, String valor) {
    this.var = var;
    this.tipo = tipo;
  }

  public void setVar(String var) {
    this.var = var;
  }

  public void setTipo(Symbol tipo) {
    this.tipo = tipo;
  }

  public void genC(PW pw) {
    pw.println("var" + this.var + ":" + this.tipo + ";");
  }

  public String getVar() {
    return this.var;
  }

  public Symbol getTipo() {
    return this.tipo;
  }
}
