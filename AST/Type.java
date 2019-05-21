import AST

java.util.ArrayList

import lexer.*

//Type ::= "Int" | "Boolean" | "String"

public Class Type{
  private Symbol type;

  public Type(Symbol a)
    this.type = a;

  public tipo(PW pw){
    if(type == Symbol.INT)
      pw.println("Int");
    else if (type == Symbol.BOOLEAN)
     pw.println("Boolean");
    else if (type == Symbol.STRING)
      pw.println("String");
  }

  public getType(){
    return this.type;
  }
}
