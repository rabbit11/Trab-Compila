package AST;

import Lexer.Symbol;

import java.util.ArrayList;
import Lexer.*;

//Type ::= "Int" | "Boolean" | "String"

abstract public class Type {
  private Symbol type;
  private String name;

  public Type(String name) {
    this.name = name;
  }

  public static Type booleanType = new BooleanType();
  public static Type integerType = new IntegerType();
  public static Type charType = new CharType();

  
  abstract public String getCname();
  
  public String getName() {
    return name;
  }
  
  public Symbol getType(){
    return this.type;
  }
  
        // public void tipo(PW pw){
        //   if(type == Symbol.INT)
        //     pw.println("int");
        //   else if (type == Symbol.BOOLEAN)
        //    pw.println("Boolean");
        //   else if (type == Symbol.STRING)
        //     pw.println("String");
        // }
}
