//     Nome                    RA
// Bruno Asti Baradel      726499
// Pablo Laranjo           726577
// Pedro Coelho            743585
// Vinícius Crepschi       743601

package Error;

import Lexer.*;
import java.io.*;

import AST.PW;

public class CompilerError{
    
    public CompilerError(PW pw, String fileName){
        this.pw = pw;
        this.fileName = fileName;
        this.signaled = false;
    }

    public void setLexer(Lexer lexer){
        this.lexer = lexer;
    }

    public boolean errorSignaled(){
        return signaled;
    }

    public void Error(String message){
        if(errorSignaled()){
            pw.println(message);
        }
        else {
            String errorMessage = this.fileName + ": " + lexer.getLineNumber() + message;
            
            pw.println(errorMessage);
            pw.println(lexer.getCurrentLine());

            this.signaled = true;
        }
    }

    private PW pw;
    private String fileName;
    private boolean signaled;
}