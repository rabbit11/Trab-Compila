import AST

java.util.ArrayList

import lexer.*


public Class Type{
  private Symbol type;

  public type(PW pw){
    if(type == Symbol.INT)
      pw.println("Int");
    else if (type == Symbol.Boolean)
     pw.println("Boolean");
    else if (type == Symbol.String)
      pw.println("String");
  }
}
