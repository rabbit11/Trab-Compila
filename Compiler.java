//     Nome                    RA
// Bruno Asti Baradel      726499
// Pablo Laranjo           726577
// Pedro Coelho            743585
// Vinícius Crepschi       743601

/*
    comp7

private void error(String errorMsg) => Error Message

Class Hashtable has methods
    Object put(Object key, Object value)
    Object get(Object key)

Method put inserts value at the table using key as its name (or key!). It returns the table object that had key key. If there were none, it returns null:

  Hashtable h = new Hashtable();
  h.put( "one", new Integer(1) );
  h.put( "two", new Integer(2) );
  Object obj = h.put("one", "I am the one");
  System.out.println(obj);
  // prints 1
  obj = h.get("two");
  System.out.println(obj);
  // prints 2
  System.out.println( h.get("one") ); // prints "I am the one"
  char name = ’a’;
  h.put( name + "", "This is an a");
  System.out.println( h.get("a") );

In the last call to put, we used name + "" as the key. We could not have used just name because name is not an object.

    This compiler uses the same grammar as compiler 6:
       Program ::= VarDecList ':' Expr
       VarDecList ::=  | VarDec VarDecList
       VarDec ::= Letter '=' Number
       Expr::=  '(' oper Expr  Expr ')' | Number | Letter
       Oper ::= '+' | '-' | '*' | '/'
       Number ::= '0'| '1' | ... | '9'
       Letter ::= 'A' | 'B'| ... | 'Z'| 'a'| 'b' | ... | 'z'

     The compiler evaluate the expression

*/

import AST.*;
import Lexer.*;
import java.util.ArrayList;
import java.util.Hashtable;

public class Compiler {
  private Lexer lexer;
  private SymbolTable table;

  public Program compile(char[] p_input) {
    //error = new CompilerError(null); //COMENTADO PARA COMPILAR
    lexer = new Lexer(p_input, error);
    table = new SymbolTable();
    error.setLexer(lexer);

    input = p_input;
    tokenPos = 0;

    symbolTable = new Hashtable();

    lexer.nextToken();

    Program e = program();
    if (tokenPos != input.length)
      error("Fim de codigo esperado!");

    return e;
  }

  // Program ::= Func {Func}
  private Program program() {
    func();

    return new Program(esq, dir);

    //ESQ E DIR NAO EXISTEM
  }

  private void RelOp() {
    if (lexer.token == Symbol.EQUAL || lexer.token == Symbol.LT || lexer.token == Symbol.LTE || lexer.token == Symbol.GT
        || lexer.token == Symbol.GTE) {

    } else {
      System.out.println("Operador não reconhecido");
    }
    lexer.nextToken();
  }

  // ReturnStat ::= "return" Expr ";"
  public ReturnStat ReturnStat(ReturnStat stat) {

    // ReturnStat();

    if (lexer.token != Symbol.RETURN) {
      System.out.println("Expected 'Return' but found '" + lexer.getStringValue() + "'");
    }

    lexer.nextToken();

    // FLAG = RETURN;

    expr(stat.Expr()); //SUBSTITUIDO GETEXPR (QUE NAO EXISTIA) POR EXPR

    if (lexer.token != Symbol.SEMICOLON) {
      System.out.println("Expected ';' but found '" + lexer.getStringValue() + "'");
    }

    lexer.nextToken();
  }

  /*
   * Statement ::= AssignmentStatement | IfStatement | ReadStatement |
   * WriteStatement
   */
  private Stat stat() {
    switch (lexer.token) {
    case IDLITERAL:
      return assignExprStat();
    case VAR:
      return varDecStat();
    case IF:
      return ifStat();
    case WHILE:
      return whileStat();
    default:
      // will never be executed
      System.out.println("Statement expected");
    }
    return null;
  }

  // StatList ::= "{"{Stat}"}"
  private StatList statList() {
    ArrayList<Stat> v = new ArrayList<Stat>();
    // statements always begin with an identifier, if, read or write
    while (lexer.token == Symbol.IDENT || lexer.token == Symbol.IF || lexer.token == Symbol.READ
        || lexer.token == Symbol.WRITE) { //NÃO EXISTE SIMBOLOS IDENT, READ E WRITE
      v.add(stat());
      if (lexer.token != Symbol.SEMICOLON)
        System.out.println("; expected");
      lexer.nextToken();
    }
    return new StatList(v);
  }

  private IfStat ifStat() {

    if (lexer.token != Symbol.IF) {
      System.out.println("'if' expected");
    }

    lexer.nextToken();

    Expr e = exprOr(lexer.token); //NÃO TEMOS A FUNÇÃO EXPROR

    lexer.nextToken();

    // devemos checar pela abertura e fechamento de chaves?

    if (lexer.token != Symbol.LBRA) {
      System.out.println("{ expected");
    }

    StatList ifPart = statList();
    StatList elsePart = null;

    if (lexer.token != Symbol.RBRA) {
      System.out.println("} expected");
    }

    lexer.nextToken();

    if (lexer.token == Symbol.ELSE) {

      if (lexer.token != Symbol.LBRA) {
        System.out.println("{ expected");
      }

      lexer.nextToken();
      elsePart = statList();
      lexer.nextToken();

      if (lexer.token != Symbol.RBRA) {
        System.out.println("} expected");
      }
    }

    lexer.nextToken(); // não sei se precisa

    return new IfStat(e, ifPart, elsePart);

  }

  // ****precisa ser checado****
  // Type::="Int"|"Boolean"|"String"
  private Type type() {
    Type result;
    switch (lexer.token) {
    case INT:
      System.out.println("int");
      // result = Type.integerType;
      break;
    case BOOLEAN:
      // result = Type.booleanType;
      break;
    case STRING:
      // result = Type.charType;
      break;
    default:
      System.out.println("Type expected");
      result = null;
    }
    lexer.nextToken();
    return result;
  }

  private VarDecStat varDecStat() {

    if (lexer.token != Symbol.VAR)// ta certo isso?
      System.out.println("Identifier expected");

    lexer.nextToken(); // ta certo isso?

    Type typeVar = type();

    // name of the identifier
    String name = lexer.getStringValue();

    lexer.nextToken();

    if (lexer.token != Symbol.SEMICOLON) {
      System.out.println("; expected");
    }

    lexer.nextToken();

    VarDecStat v = new VarDecStat(name, typeVar);
    return v;
  }

  private WhileStat whileStat() {
    if (lexer.token != Symbol.WHILE) {
      System.out.println("'while' expected");
    }

    lexer.nextToken();

    Expr e = exprOr(lexer.token); //NÃO TEMOS A FUNÇÃO EXPROR

    lexer.nextToken();

    StatList whilePart = statList();

    lexer.nextToken(); // não sei se precisa

    return new WhileStat(whilePart, e);

  }

  public boolean LiteralBoolean() {

    if (lexer.token == Symbol.BOOLLITERAL) {
      boolean boo = lexer.getBoolValue();
      lexer.nextToken();
      return boo;
    } else {
      System.out.println("Error in the boolean type");
    }

    return false; //ALTERADO RETORNO DE NULL PARA FALSE
  }

  private ParamList ParamList() {
    // ParamList ::= ParamDec { ’,’ ParamDec }
    ParamList paramList = null;

    ParamList a = new ParamList(); //ADICIONADO A LETRA 'a' PQ NAO TINHA NADA NO LUGAR (ANTIGO: ParamList = new ParamList();)
    ParamDec();
    while (lexer.token == Symbol.COMMA) {
      lexer.nextToken();
      ParamDec();
    }

    return paramList;
  }

  private void ParamDec() {
    // ParamDec ::= Id ":" Type
    if (lexer.token != Symbol.IDLITERAL) {
      // error.signal("Identifier expected");
      System.out.println("identifier expected");
    }
    // name of the identifier
    String name = (String) lexer.getStringValue();
    lexer.nextToken();

    if (lexer.token != Symbol.COLON) { // :
      System.out.println(": expected");
      // error.show(": expected");
    } else {
      lexer.nextToken();
    }
    // get the type
    Type typeVar = type();
  }

  private void error(String errorMsg) {
    if (tokenPos == 0)
      tokenPos = 1;
    else if (tokenPos >= input.length)
      tokenPos = input.length;

    String strInput = new String(input, tokenPos - 1, input.length - tokenPos + 1);
    String strError = "Error at \"" + strInput + "\"";
    System.out.println(strError);
    System.out.println(errorMsg);
    throw new RuntimeException(strError);
  }

  // ExprAnd::= ExprRel {”and”ExprRel}
  private Expr exprAnd() {
    Expr esq, dir;
    esq = exprRel();

    if (lexer.token == Symbol.AND) {
      lexer.nextToken();
      dir = ExprRel(); //ERRO AQUI, NAO DESCOBRI O MOTIVO
      esq = new ExprAnd(esq, Symbol.AND, dir); //EXPRAND ALTERADO DE exprAnd para ExprAnd
    } else
      System.out.println("'and' expected");

    return esq;
  }

  // ExprLiteral ::= LiteralInt | LiteralBoolean | LiteralString
  public Expr exprLiteral() {
    int flag = 0;

    if(lexer.token==Symbol.INTLITERAL){
      lexer.nextToken();
      return Symbol.INTLITERAL;
    }
    else flag=1;

    if(lexer.token==Symbol.BOOLLITERAL){
        lexer.nextToken();
        return Symbol.BOOLLITERAL;
    }

    if (lexer.token == Symbol.EQUAL || lexer.token == Symbol.LT || lexer.token == Symbol.LTE || lexer.token == Symbol.GT
        || lexer.token == Symbol.GTE) {

    } else
      flag = 2;


    if(lexer.token == Symbol.STRINGLITERAL){
      lexer.nextToken();
      return Symbol.STRINGLITERAL;
    }
    else
      flag = 3;

    if(flag == 1)
      System.out.println("Expected type 'int'");
    else if(flag == 2)
      System.out.println("Expected type 'boolean'");
    else if(flag == 3)
      System.out.println("Expected type 'string'");
}

  // Expr ::= ExprAnd {”or”ExprAnd}
  public Expr expr() {
    Expr esq, dir;
    esq = ExprAnd(); //ERRO AQUI, NAO SEI PQ

    while ((op = lexer.token) == Symbol.OR) {
      lexer.nextToken();
      dir = exprMult();
    }
    return left;
  }

  // ExprAdd ::= ExprMult {(” + ” | ” − ”)ExprMult}
  private Expr exprAdd() {
    Symbol op;
    Expr esq, dir;
    esq = ExprMult(); //EXPRMULT ALTERADO DE exprMult para ExprMult

    while ((op = lexer.token) == Symbol.PLUS || op == Symbol.MINUS) { //ERRO NO OP AQUI (SERIA A ATRIBUIÇÃO?)
      lexer.nextToken();
      dir = ExprMult(); //EXPRMULT ALTERADO DE exprMult para ExprMult
      esq = new ExprAdd(esq, op, dir);
    }
    return esq;
  }

  // AssignExprStat ::= Expr [ "=" Expr ] ";"
  public Expr assignExprStat() {
    Expr esq, dir;
    esq = expr();

    if (lexer.token == Symbol.ASSIGN) {
      lexer.nextToken();
      dir = expr();
      lexer.nextToken();
      if (lexer.token == Symbol.SEMICOLON)
        lexer.nextToken();
      return esq;
    }

    else
      error("simbolo incorreto");
  }

  private Expr exprRel() {
    // ExprRel ::= ExprAdd [ RelOp ExprAdd ]
    Expr left, right;
    left = exprAdd();
    Symbol op = lexer.token;
    if (op == Symbol.EQUAL || op == Symbol.DIFFERENT || op == Symbol.LTE || op == Symbol.LT || op == Symbol.GTE
        || op == Symbol.GT) {
      lexer.nextToken();
      right = exprAdd();

      left = new ExprRel(left, op, right);
    }
    return left;
  }

  private Expr exprUnary() {
    // ExprUnary ::= [ ( "+" | "-" ) ] ExprPrimary
    Symbol op = lexer.token;
    if (op == Symbol.PLUS || op == Symbol.MINUS)
      lexer.nextToken();

    Expr e = exprPrimary();

    return new ExprUnary(e, op);
  }

  private Expr exprPrimary() {
    // ExprPrimary ::= Id | FuncCall | ExprLiteral

    // ?
  }

  private Func func() {
    // Func ::= "function" Id [ "(" ParamList ")" ] ["->" Type ] StatList
    if (lexer.token != Symbol.FUNCTION) {
      // should never occur
      System.out.println("Internal compiler error");
      return null;
    }
    lexer.nextToken();

    if (lexer.token != Symbol.IDLITERAL)
      System.out.println("Identifier expected");

    String name = (String) lexer.getStringValue();
    Subroutine s = (Subroutine) symbolTable.returnGlobal(name); //SUBROUTINE NAO EXISTE!!

    lexer.nextToken();

    // currentFunction is used to store the function being compiled
    s = currentFunction = new Func(name); //CURRENTFUNCTION NAO EXISTE

    // insert s in the symbol table
    symbolTable.putGlobal(name, s);

    if (lexer.token == Symbol.LPAR) {
      lexer.nextToken();
      s.setParams(ParamList()); //TROCADO DE paramList para ParamList
      if (lexer.token != Symbol.RPAR) {
        System.out.println(") expected");
      } else
        lexer.nextToken();
    }

    if (lexer.token == Symbol.ARROW) {
      lexer.nextToken();
      ((Func) s).setReturnType(type());
    }

    s.setCorpo(statList());

    SymbolTable.resetLocal(); //TROCADO DE symbolTable para SymbolTable
    return s;
  }

  private FuncCall funcCall() {
    // FuncCall ::= Id "(" [ Expr {”, ”Expr} ] ")"
    if (lexer.token != Symbol.IDLITERAL)
      System.out.println("Identifier expected");

    lexer.nextToken();
    String name = (String) lexer.getStringValue();

    Func p = (Func) SymbolTable.returnGlobal(name); //TROCADO DE symbolTable para SymbolTable
    //TROCADO DE Function para Func

    if (lexer.token != Symbol.LPAR) {
      System.out.println("( expected");
    } else
      lexer.nextToken();
    if (lexer.token != Symbol.RPAR) {
      Expr e = expr();
      lexer.nextToken();
      while (lexer.token == Symbol.COMMA) {
        lexer.nextToken(); // space
        e = expr();
      }

      lexer.nextToken();
      if (lexer.token != Symbol.RPAR) {
        System.out.println(") expected");
      }
    }
    // return ?????
  }

  private char token;
  private int tokenPos;
  private char[] input;
  private Hashtable<Character, VarDecStat> symbolTable;
}
