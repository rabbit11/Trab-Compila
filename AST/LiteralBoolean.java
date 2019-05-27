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
