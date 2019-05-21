// VarDecStat ::= "var" Id ":" Type ";"
// package AST;

import java.util.*;
import Lexer.*;
import AST.Type;

public class VarDecStat {
  private String var;
  private Type tipo;

  public VarDecStat(String var, Type tipo, String valor) {
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
