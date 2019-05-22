package AST;

import Lexer.*;

abstract public class Expr {

    abstract public void genC(PW pw);
}