//     Nome                    RA
    // Bruno Asti Baradel      726499
    // Pablo Laranjo           726577
    // Pedro Coelho            743585
    // Vinícius Crepschi       743601

package AST;

public class Variable extends Expr {

  public Variable(String name) {
    this.name = name;
  }

  public void genC(PW pw) {
    pw.out.print(name);
  }

  public String getName(){
    return this.name;
  }
  private String name;
}
