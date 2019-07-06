//     Nome                    RA
    // Bruno Asti Baradel      726499
    // Pablo Laranjo           726577
    // Pedro Coelho            743585
    // Vinícius Crepschi       743601

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
      pw.print("<");
    else if (getType() == Symbol.GT)
      pw.print(">");
    else if (getType() == Symbol.LTE)
      pw.print(">=");
    else if (getType() == Symbol.GTE)
      pw.print("<=");
    else if (getType() == Symbol.EQUAL)
      pw.print("==");
    else if (getType() != Symbol.DIFFERENT)
      pw.print("=");
  }

  public Symbol getType() {
    return this.op;
  }
}
