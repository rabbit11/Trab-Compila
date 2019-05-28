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

  public String tipo(PW pw) {
    if (type == Symbol.INT){
      pw.println("int");
      return "int";
    }

    else if (type == Symbol.BOOLEAN){
      pw.println("Boolean");
      return "Boolean";
    }
    
    else if (type == Symbol.STRING){
      pw.println("String");
      return "String";
    }
  }

  public Symbol getType() {
    return this.type;
  }
}