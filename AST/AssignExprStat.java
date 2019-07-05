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
    private boolean readInt, readString;
    public AssignExprStat(Expr esq, Expr dir, boolean readInt, boolean readString) {
        this.esq = esq;
        this.dir = dir;
        this.readInt = readInt;
        this.readString = readString;
    }

    @Override
    public void genC(PW pw){
        //checando se há uma atribuição de strings (algo ilegal em C)
        //checando operações de leitura
        if(readInt == true){
            pw.print("scanf(\"%d\", &");
            this.esq.genC(pw);
            pw.println(");");
        }
        
        else if(readString == true){
            pw.print("scanf(\"%s\", ");
            this.esq.genC(pw);
            pw.println(");");
        }

        else if(this.esq.getType().getType() == Symbol.STRING || 
            this.esq.getType().getType() == Symbol.STRINGLITERAL){

            if(this.dir != null){
                pw.print("strcpy(");
                this.esq.genC(pw);
                pw.print(", ");
                this.dir.genC(pw);
                pw.println(");");
            }
        }
        else{
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
    }

    public Expr getEsq(){
        return this.esq;
    }

    public Expr getDir(){
        return this.dir;
    }
}
