package AST;

import java.util.ArrayList;

import Lexer.*;

//Func ::= "function" Id [ "(" ParamList ")" ] ["->" Type ] StatList

//conferir após implementação de var e type
public class Func {
    private Symbol tipoRetorno;
    private String nomeFunc;
    private ArrayList<ParamDec> params;
    //private FuncCorpo corpo;

    public Func(Symbol tipoRetorno, String nomeFunc, ParamList p) {
        this.tipoRetorno = tipoRetorno;
        this.nomeFunc = nomeFunc;
        this.params = p.getListaStats();
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
                if (params.get(i).getTipo() == Symbol.INT) {
                    pw.print("Int " + params.get(i).getVar());
                } else if (params.get(i).getTipo() == Symbol.BOOLEAN){
                    pw.print("Boolean " + params.get(i).getVar());
                }else if (params.get(i).getTipo() == Symbol.STRING){
                    pw.print("String " + params.get(i).getVar());
                }
            } else {
                if (params.get(i).getTipo() == Symbol.INT) {
                    pw.print("Int " + params.get(i).getVar() + ", ");
                } else if (params.get(i).getTipo() == Symbol.BOOLEAN){
                    pw.print("Boolean " + params.get(i).getVar() + ", ");
                }else if (params.get(i).getTipo() == Symbol.STRING){
                    pw.print("String " + params.get(i).getVar());
                }
            }
        }
        pw.println(")");
        pw.println("{");

        // Incrementa a tabulação
        pw.add();

        // statList

        // Decrementa a tabulação e fecha a função
        pw.sub();
        pw.println("}");

    }

    public void setParams(ArrayList<ParamDec> var) {
        this.params = var;
    }

   /* public void setCorpo(FuncCorpo corpo) {
        this.corpo = corpo;
    }
    */ //FOI COMENTADO PARA COMPILAR

    public String getNome() {
        return this.nomeFunc;
    }

    public Symbol getTipo() {
        return this.tipoRetorno;
    }

    public ArrayList<ParamDec> getParams() {
        return this.params;
    }
}
