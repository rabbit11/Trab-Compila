//     Nome                    RA
    // Bruno Asti Baradel      726499
    // Pablo Laranjo           726577
    // Pedro Coelho            743585
    // Vinícius Crepschi       743601

package AST;

import java.util.ArrayList;

import Lexer.Symbol;

//FuncCall ::= Id "(" [ Expr {”, ”Expr} ] ")"

public class FuncCall extends Expr{
    private String funcName;
    private ArrayList<Expr> arrayExpr;
    Type tipo;

    public FuncCall(String s, ArrayList<Expr> e, Type tipo) {
        this.funcName = s;
        this.arrayExpr = e;
        this.tipo = tipo;
    }

    public FuncCall(String s, ArrayList<Expr> e) {
        this.funcName = s;
        this.arrayExpr = e;
        this.tipo = null;
    }

    public FuncCall(String s, Type tipo) {
        this.funcName = s;
        this.arrayExpr = null;
        this.tipo = tipo;
    }

    public FuncCall(String s) {
        this.funcName = s;
        this.arrayExpr = null;
        this.tipo = null;
    }

    public void genC(PW pw) {
      if(this.funcName.equals("readInt")){
          pw.print("scanf(\"%d\" , &x)");
      }

      else if(this.funcName.equals("readString")) {
          pw.print("scanf(\"%s\" , &x)");
      }

      else if(this.funcName.equals("writeln")){
          if(this.arrayExpr != null){
            if(this.arrayExpr.get(0).getType().getType() == Symbol.INT){
                pw.print("printf(" + "\"%d");
                pw.print("\\n\", ");
                this.arrayExpr.get(0).genC(pw);
                pw.print(")");
            }
            else if (this.arrayExpr.get(0).getType().getType() == Symbol.STRING) {
                pw.print("printf(" + "\"%s\"");
                pw.print("\\n\", ");
                this.arrayExpr.get(0).genC(pw);
                pw.print(")");
            }
            else if (this.arrayExpr.get(0).getType().getType() == Symbol.BOOLEAN) {
                pw.print("printf(" + "\"%d\"");
                pw.print("\\n\", ");
                this.arrayExpr.get(0).genC(pw);
                pw.print(")");
            }
            else if (this.arrayExpr.get(0).getType().getType() == Symbol.INTLITERAL) {
                pw.print("printf(");
                pw.print("\\n\", ");
                this.arrayExpr.get(0).genC(pw);
                pw.print(")");
            }
            else if (this.arrayExpr.get(0).getType().getType() == Symbol.STRINGLITERAL) {
                pw.print("printf(");
                pw.print("\\n\", ");            
                this.arrayExpr.get(0).genC(pw);
                pw.print(")");
            }
            else if (this.arrayExpr.get(0).getType().getType() == Symbol.BOOLLITERAL) {
                pw.print("printf(");
                pw.print("\\n\", ");
                this.arrayExpr.get(0).genC(pw);
                pw.print(")");
            }
          }
          else{
              pw.print("printf(" + "\n" + ")");
          }
      }

      else if(this.funcName.equals("write")){
            if (this.arrayExpr != null) {
                if (this.arrayExpr.get(0).getType().getType() == Symbol.INT) {
                    pw.print("printf(" + "\"%d\", ");
                    this.arrayExpr.get(0).genC(pw);
                    pw.print(")");
                } else if (this.arrayExpr.get(0).getType().getType() == Symbol.STRING) {
                    pw.print("printf(" + "\"%s\", ");
                    this.arrayExpr.get(0).genC(pw);
                    pw.print(")");
                } else if (this.arrayExpr.get(0).getType().getType() == Symbol.BOOLEAN) {
                    pw.print("printf(" + "\"%d\", ");
                    this.arrayExpr.get(0).genC(pw);
                    pw.print(")");
                } else if (this.arrayExpr.get(0).getType().getType() == Symbol.INTLITERAL) {
                    pw.print("printf(");
                    this.arrayExpr.get(0).genC(pw);
                    pw.print(")");
                } else if (this.arrayExpr.get(0).getType().getType() == Symbol.STRINGLITERAL) {
                    pw.print("printf(");
                    this.arrayExpr.get(0).genC(pw);
                    pw.print(")");
                } else if (this.arrayExpr.get(0).getType().getType() == Symbol.BOOLLITERAL) {
                    pw.print("printf(");
                    this.arrayExpr.get(0).genC(pw);
                    pw.print(")");
                }
            } else {
                pw.print("printf(" + " " + ")");
            }
      }

      else{
        pw.print(this.funcName);
        pw.print("(");
  
        if(this.arrayExpr != null){
          int i = 0;
          
          for (Expr p : arrayExpr) {
              p.genC(pw);
              i++;
  
              if (i > 0 && i < arrayExpr.size()) {// garante que imprimimos , apenas quando tem mais de 1 param
                  pw.printNI(", "); // e não imprimimos após o último
              }
          }
          pw.print(")");
        }
      }
    }

    public void setArrayExpr(ArrayList<Expr> e) {
        this.arrayExpr = e;
    }

    public Type getType(){
        return this.tipo;
    }
}
