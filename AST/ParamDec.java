// ParamDec ::= Id ":" Type

import java.util.*;
import Lexer.*;
import AST.Type;

public class ParamDec {
  private String var;
  private Type tipo;

  public ParamDec(String var, Type tipo) {
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
    pw.println(this.var + ":" + this.tipo.getType());
  }

  public String getVar() {
    return this.var;
  }

  public Symbol getTipo() {
    return this.tipo.getType();
  }
}
