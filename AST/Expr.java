package AST;

import Lexer.*;

//Expr ::= ExprAnd {”or”ExprAnd}

abstract public class Expr {

    abstract public void genC(PW pw);
}
