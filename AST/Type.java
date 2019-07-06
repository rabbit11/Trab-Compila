//     Nome                    RA
    // Bruno Asti Baradel      726499
    // Pablo Laranjo           726577
    // Pedro Coelho            743585
    // Vinícius Crepschi       743601

package AST;

import Lexer.Symbol;

//Type ::= "Int" | "Boolean" | "String"

public class Type {
  private Symbol type;

  public Type(Symbol a) {
    this.type = a;
  }

  public String tipo(PW pw) {
    if (type == Symbol.INT){
      // pw.println("int");
      return "int";
    }

    else if (type == Symbol.BOOLEAN){
      // pw.println("Boolean");
      return "bool";
    }

    else if (type == Symbol.STRING){
      // pw.println("String");
      return "char";
    }
    return "Tipo não reconhecido";
  }

  public Symbol getType() {
    return this.type;
  }
}
