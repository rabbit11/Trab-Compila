package AST;

import Lexer.Symbol;

import java.util.ArrayList;
import Lexer.*;

// RelOp::="<"|"<="|">"|">="|"=="|"!="

public class RelOp {
  private Symbol op;

  public RelOp(Symbol a) {
    this.op = a;
  }

  public void tipo(PW pw) {
    if (getType() == Symbol.LT)
      pw.println("<");
    else if (getType() == Symbol.GT)
      pw.println(">");
    else if (getType() == Symbol.LTE)
      pw.println(">=");
    else if (getType() == Symbol.GTE)
      pw.println("<=");
    else if (getType() == Symbol.EQUAL)
      pw.println("==");
    else if (getType() != Symbol.DIFFERENT)
      pw.println("=");
  }

  public Symbol getType() {
    return this.op;
  }
}
