//     Nome                    RA
    // Bruno Asti Baradel      726499
    // Pablo Laranjo           726577
    // Pedro Coelho            743585
    // Vinícius Crepschi       743601

// ParamDec ::= Id ":" Type
package AST;
import java.util.*;
import Lexer.*;
import AST.Type;

public class ParamDec extends Expr{
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
    //pw.print(this.var);
    //pw.print(this.tipo.getType());
    pw.print(this.tipo.tipo(pw) + " " + this.var);
  }

  public String getVar() {
    return this.var;
  }

  public Type getType() {
    return this.tipo;
  }
}
