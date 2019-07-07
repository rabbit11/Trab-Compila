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
      pw.printNI("<");
    else if (getType() == Symbol.GT)
      pw.printNI(">");
    else if (getType() == Symbol.LTE)
      pw.printNI(">=");
    else if (getType() == Symbol.GTE)
      pw.printNI("<=");
    else if (getType() == Symbol.EQUAL)
      pw.printNI("==");
    else if (getType() != Symbol.DIFFERENT)
      pw.printNI("=");
  }

  public Symbol getType() {
    return this.op;
  }
}
