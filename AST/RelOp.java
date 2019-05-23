// RelOp::="<"|"<="|">"|">="|"=="|"!="

package AST;

import Lexer.Symbol;

import java.util.ArrayList;
import Lexer.*;

public class RelOp {
  private Symbol op;

  public RelOp(Symbol a) {
    this.op = a;
  }

  public void tipo(PW pw) {
    if (type == Symbol.LT)
      pw.println("<");
    else if (type == Symbol.GT)
      pw.println(">");
    else if (type == Symbol.LTE)
      pw.println(">=");
    else if (type == Symbol.GTE)
      pw.println("<="); 
    else if (type == Symbol.EQUAL)
      pw.println("==");
    else if (type != Symbol.DIFFERENT)
      pw.println("=");
  }

  public Symbol getType() {
    return this.type;
  }
}
