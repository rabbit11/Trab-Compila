package AST;

import Lexer.Symbol;

import java.util.ArrayList;
import Lexer.*;

//Type ::= "Int" | "Boolean" | "String"

public class Type {
  private Symbol type;

  public Type(Symbol a) {
    this.type = a;
  }

  public void tipo(PW pw) {
    if (type == Symbol.INT)
      pw.println("int");
    else if (type == Symbol.BOOLEAN)
      pw.println("Boolean");
    else if (type == Symbol.STRING)
      pw.println("String");
  }

  public Symbol getType() {
    return this.type;
  }
}