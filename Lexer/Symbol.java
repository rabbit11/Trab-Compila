//     Nome                    RA
    // Bruno Asti Baradel      726499
    // Pablo Laranjo           726577
    // Pedro Coelho            743585
    // Vinícius Crepschi       743601

package Lexer;

public enum Symbol {
  EOF("eof"),
  INTLITERAL("IntLiteral"),
  IDLITERAL("IdLiteral"),
  BOOLLITERAL("BoolLiteral"),
  STRINGLITERAL("StringLiteral"),
  FUNCTION("function"),
  IF("if"),
  ELSE("else"),
  RETURN("return"),
  WHILE("while"),
  VAR("var"),
  INT("Int"),
  STRING("String"),
  BOOLEAN("Boolean"),
  TRUE("true"),
  FALSE("false"),
  AND("and"),
  OR("or"),
  ARROW("->"),
  PLUS("+"),
  MINUS("-"),
  MULT("*"),
  DIV("/"),
  EQUAL("=="),
  LT("<"),
  LTE("<="),
  GT(">"),
  GTE(">="),
  LPAR("("),
  RPAR(")"),
  LBRA("{"),
  RBRA("}"),
  ASSIGN("="),
  DIFFERENT("!="),
  COMMA(","),
  COLON(":"),
  SEMICOLON(";");

  Symbol(String name) {
    this.name = name;
  }

  public String toString() {
    return name;
  }

  private String name;
}
