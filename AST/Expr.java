//     Nome                    RA
    // Bruno Asti Baradel      726499
    // Pablo Laranjo           726577
    // Pedro Coelho            743585
    // Vinícius Crepschi       743601

package AST;

//Expr ::= ExprAnd {”or”ExprAnd}

abstract public class Expr {

    abstract public void genC(PW pw);
    abstract public Type getType();
}
