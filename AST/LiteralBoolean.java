//     Nome                    RA
    // Bruno Asti Baradel      726499
    // Pablo Laranjo           726577
    // Pedro Coelho            743585
    // Vinícius Crepschi       743601

package AST;

import Lexer.Symbol;

import java.util.ArrayList;
import Lexer.*;

// LiteralBoolean::="true"|"false"

public class LiteralBoolean {
  private Symbol value;

  public LiteralBoolean(Symbol a) {
    this.value = a;
  }

  public void writeLiteral(PW pw) {
    if (value == Symbol.TRUE)
      pw.println("true");
    else if (value == Symbol.FALSE)
      pw.println("false");
  }

  public Symbol getValue() {
    return this.value;
  }
}
