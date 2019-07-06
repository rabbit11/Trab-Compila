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
          pw.print("scanf(\"%d\", &readInt)");
      }

      else if(this.funcName.equals("readString")) {
          pw.print("scanf(\"%s\", &readString)");
      }

      else if(this.funcName.equals("writeln")){
          if(this.arrayExpr != null){
            if(this.arrayExpr.get(0).getType().getType() == Symbol.INT){
                pw.printNI("printf(" + "\"%d");
                pw.printNI("\\n\", ");
                this.arrayExpr.get(0).genC(pw);
                pw.printNI(")");
            }
            else if (this.arrayExpr.get(0).getType().getType() == Symbol.STRING) {
                pw.printNI("printf(" + "\"%s");
                pw.printNI("\\n\", ");
                this.arrayExpr.get(0).genC(pw);
                pw.printNI(")");
            }
            else if (this.arrayExpr.get(0).getType().getType() == Symbol.BOOLEAN) {
                pw.printNI("printf(" + "\"%d");
                pw.printNI("\\n\", ");
                this.arrayExpr.get(0).genC(pw);
                pw.printNI(")");
            }
            else if (this.arrayExpr.get(0).getType().getType() == Symbol.INTLITERAL) {
                pw.printNI("printf(");
                this.arrayExpr.get(0).genC(pw);
                pw.printNI("\\n\"");
                pw.printNI(")");
            }
            else if (this.arrayExpr.get(0).getType().getType() == Symbol.STRINGLITERAL) {
                pw.printNI("printf(");
                this.arrayExpr.get(0).genC(pw);
                pw.printNI("\\n\"");            
                pw.printNI(")");
            }
            else if (this.arrayExpr.get(0).getType().getType() == Symbol.BOOLLITERAL) {
                pw.printNI("printf(");
                this.arrayExpr.get(0).genC(pw);
                pw.printNI("\\n\"");
                pw.printNI(")");
            }
          }
          else{
              pw.printNI("printf(" + "\n" + ")");
          }
      }

      else if(this.funcName.equals("write")){
            if (this.arrayExpr != null) {
                if (this.arrayExpr.get(0).getType().getType() == Symbol.INT) {
                    pw.printNI("printf(" + "\"%d\", ");
                    this.arrayExpr.get(0).genC(pw);
                    pw.printNI(")");
                } else if (this.arrayExpr.get(0).getType().getType() == Symbol.STRING) {
                    pw.printNI("printf(" + "\"%s\", ");
                    this.arrayExpr.get(0).genC(pw);
                    pw.printNI(")");
                } else if (this.arrayExpr.get(0).getType().getType() == Symbol.BOOLEAN) {
                    pw.printNI("printf(" + "\"%d\", ");
                    this.arrayExpr.get(0).genC(pw);
                    pw.printNI(")");
                } else if (this.arrayExpr.get(0).getType().getType() == Symbol.INTLITERAL) {
                    pw.printNI("printf(");
                    this.arrayExpr.get(0).genC(pw);
                    pw.printNI(")");
                } else if (this.arrayExpr.get(0).getType().getType() == Symbol.STRINGLITERAL) {
                    pw.printNI("printf(");
                    this.arrayExpr.get(0).genC(pw);
                    pw.printNI("\")");
                } else if (this.arrayExpr.get(0).getType().getType() == Symbol.BOOLLITERAL) {
                    pw.printNI("printf(");
                    this.arrayExpr.get(0).genC(pw);
                    pw.printNI(")");
                }
            } else {
                pw.print("printf(" + " " + ")");
            }
      }

      else{
        pw.printNI(this.funcName);
        pw.printNI("(");
  
        if(this.arrayExpr != null){
          int i = 0;
          
          for (Expr p : arrayExpr) {
              p.genC(pw);

              if(p.getType().getType() == Symbol.STRINGLITERAL){
                pw.printNI("\"");  
              }
              i++;
  
              if (i > 0 && i < arrayExpr.size()) {// garante que imprimimos , apenas quando tem mais de 1 param
                  pw.printNI(", "); // e não imprimimos após o último
              }
          }
          pw.printNI(")");
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
