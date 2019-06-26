//     Nome                    RA
    // Bruno Asti Baradel      726499
    // Pablo Laranjo           726577
    // Pedro Coelho            743585
    // Vinícius Crepschi       743601

package AST;

public class Variable extends Expr {

  public Variable(String name) {
    this.name = name;
    this.tipo = null;
  }

  public void genC(PW pw) {
    pw.println(name);
  }

  public String getName(){
    return this.name;
  }

  public Type getType(){
    return this.tipo;
  }

  public void setType(Type tipo){
    this.tipo = tipo;
  }

  private String name;
  private Type tipo;
}
