package AST;

import java.util.ArrayList;

import Lexer.*;

//Func ::= "function" Id [ "(" ParamList ")" ] ["->" Type ] StatList

//conferir após implementação de var e type
public class Function {
    private Symbol tipoRetorno;
    private String nomeFunc;
    private ArrayList<VarDecStat> params;
    private FuncCorpo corpo;

    public Function(Symbol tipoRetorno, String nomeFunc, ArrayList<Variable> params) {
        this.tipoRetorno = tipoRetorno;
        this.nomeFunc = nomeFunc;
        this.params = params;
    }

    public void genC(PW pw) {
        if (tipoRetorno == Symbol.INT) {
            pw.print("int " + this.nomeFunc + "("); // Com ident antes da string
        } else if (tipoRetorno == Symbol.BOOLEAN) {
            pw.print("boolean " + this.nomeFunc + "(");
        } else if (tipoRetorno == Symbol.STRING) {
            pw.print("string " + this.nomeFunc + "(");
        }

        for (int i = 0; i < params.size(); i++) {
            if (i + 1 == params.size()) {
                if (params.get(i).tipo.getType() == Symbol.INT) {
                    pw.print("int " + params.get(i).getVar());
                } else if {
                    pw.print("boolean " + params.get(i).getVar());
                }else {
                    pw.print("char * " + params.get(i).getVar());
                }
            } else {
                if (params.get(i).getTipo() == Symbol.INT) {
                    pw.print("int " + params.get(i).getVar() + ", ");
                } else if {
                    pw.print("boolean " + params.get(i).getVar() + ", ");
                }else {
                    pw.print("char * " + params.get(i).getVar() + ", ");
                }
            }
        }
        pw.println(")");
        pw.println("{");

        // Incrementa a tabulação
        pw.add();

        // Imprime o corpo da função
        corpo.genC(pw);

        // Decrementa a tabulação e fecha a função
        pw.sub();
        pw.println("}");

    }

    public void setparams(ArrayList<Variable> var) {
        this.params = var;
    }

    public void setCorpo(FuncCorpo corpo) {
        this.corpo = corpo;
    }

    public String getNome() {
        return this.nomeFunc;
    }

    public Symbol getTipo() {
        return this.tipoRetorno;
    }

    public ArrayList<Variable> getparams() {
        return this.params;
    }
}
