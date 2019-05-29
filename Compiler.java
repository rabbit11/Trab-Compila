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
    error = new CompilerError(null);
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
    Func();

    return new Program(esq, dir);
  }
  
  // Func ::= "function" Id [ "(" ParamList ")" ] ["->" Type ] StatList
  private Func func(){

  }

  private char relOp(){
    if(lexer.token == Symbol.EQUAL || lexer.token == Symbol.LT || lexer.token == Symbol.LTE
        || lexer.token == Symbol.GT || lexer.token == Symbol.GTE){

      }
    else{
      System.out.println("Operador não reconhecido");
    }
    lexer.nextToken();
  }

  // private LiteralBoolean literalBoolean(){
  //   if(lexer.token == Symbol.BOOLEAN){
  //     Symbol a = lexer.token;
      
  //     if(a == )
  //   }
  // }

  // ReturnStat ::= "return" Expr ";"
  public ReturnStat ReturnStat(ReturnStat stat){

    // ReturnStat();

    if (lexer.token != Symbol.RETURN) {
      System.out.println("Expected 'Return' but found '" + lexer.getStringValue() + "'");
    }

    lexer.nextToken();

    // FLAG = RETURN;

    expr(stat.getExpr());

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
    case IDENT:
      return assignmentStat();
    case IF:
      return ifStat();
    case READ:
      return readStat();
    case WRITE:
      return writeStat();
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
        || lexer.token == Symbol.WRITE) {
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

    Expr e = exprOr(lexer.token);

    lexer.nextToken();

    //devemos checar pela abertura e fechamento de chaves?

    if(lexer.token != Symbol.LBRA){
      System.out.println("{ expected");
    }

    StatList ifPart = statList();
    StatList elsePart = null;

    if(lexer.token != Symbol.RBRA){
      System.out.println("} expected");
    }

    lexer.nextToken();

    if(lexer.token == Symbol.ELSE){

      if(lexer.token != Symbol.LBRA){
        System.out.println("{ expected");
      }

      lexer.nextToken();
      elsePart = statList();
      lexer.nextToken();

      if(lexer.token != Symbol.RBRA){
        System.out.println("} expected");
      }
    }

    lexer.nextToken(); // não sei se precisa

    return new IfStat(e, ifPart, elsePart);

  }

  //****precisa ser checado****
  // Type::="Int"|"Boolean"|"String"
  private Type type() {
    Type result;
    switch ( lexer.token ) {
    case INT :
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

  private void varDecStat() {

    if ( lexer.token != Symbol.VAR )//ta certo isso?
      System.out.println("Identifier expected");
      
    lexer.nextToken(); // ta certo isso?

    Type typeVar = type();

    // name of the identifier
    String name = lexer.getStringValue();

    lexer.nextToken();
    
    VarDecStat v = new VarDecStat(name, typeVar);
    
    if (lexer.token != Symbol.SEMICOLON){
      System.out.println("; expected");
    }

    lexer.nextToken();
  }

  private WhileStat whileStat(){
    if(lexer.token != Symbol.WHILE){
      System.out.println("'while' expected");
    }
    
    lexer.nextToken();

    Expr e = exprOr(lexer.token);

    lexer.nextToken();
    
    StatList whilePart = statList();
   
    lexer.nextToken(); // não sei se precisa

    return new WhileStat(whilePart, e);
    
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

  private char token;
  private int tokenPos;
  private char[] input;
  private Hashtable<Character, VarDecStat> symbolTable;
}
