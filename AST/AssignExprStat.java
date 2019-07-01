//     Nome                    RA
    // Bruno Asti Baradel      726499
    // Pablo Laranjo           726577
    // Pedro Coelho            743585
    // Vinícius Crepschi       743601

package AST;
import Lexer.*;
// AssignExprStat ::= Expr [ "=" Expr ] ";"

public class AssignExprStat extends Stat {
    private Expr esq, dir;

    public AssignExprStat(Expr esq, Expr dir) {
        this.esq = esq;
        this.dir = dir;
    }

    @Override
    public void genC(PW pw){
        this.esq.genC(pw);

        if(dir != null){
            pw.print(" = ");
            this.dir.genC(pw);

            if (dir.getType().getType() == Symbol.STRINGLITERAL) {
                pw.print("\"");
            }
        }
        pw.println(";");
    }

    public Expr getEsq(){
        return this.esq;
    }

    public Expr getDir(){
        return this.dir;
    }
}
