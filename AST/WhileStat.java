//     Nome                    RA
    // Bruno Asti Baradel      726499
    // Pablo Laranjo           726577
    // Pedro Coelho            743585
    // Vinícius Crepschi       743601

package AST;

import java.util.ArrayList;

//WhileStat ::= "while" Expr StatList

public class WhileStat extends Stat {
    private Expr expr;
    private StatList whileBody;

    public WhileStat(StatList whileBody, Expr expr) {
        this.whileBody = whileBody;
        this.expr = expr;
    }

    @Override
    public void genC(PW pw) {
        pw.print("while (");
        expr.genC(pw);
        pw.printlnNI("){");

        whileBody.genC(pw);

        pw.println("}");
        }
}
