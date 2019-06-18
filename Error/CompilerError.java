//     Nome                    RA
// Bruno Asti Baradel      726499
// Pablo Laranjo           726577
// Pedro Coelho            743585
// Vinícius Crepschi       743601

package Error;

import Lexer.*;
import java.io.*;

import AST.PW;

public class CompilerError {

    private PW pw;
    private String fileName;
    private boolean signaled;
    private Lexer lexer;

    public CompilerError(PW pw, String fileName) {
        this.pw = pw;
        this.fileName = fileName;
        this.signaled = false;
    }

    public void setLexer(Lexer lexer) {
        this.lexer = lexer;
    }

    public boolean errorSignaled() {
        return signaled;
    }

    public void message(String message) {
        // if (errorSignaled()) {
        //     pw.println(message);
        //     System.out.println(message);
        // } else {
            String errMessage = this.fileName + ":" + lexer.getLineNumber() + ":" + message;

            pw.println(errMessage);
            pw.println(lexer.getCurrentLine());
            System.out.println(errMessage);
            System.out.println(lexer.getCurrentLine());

            this.signaled = true;
        // }
    }

}