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
// import java.util.Hashtable;

public class Compiler {
  private Lexer lexer;
  private SymbolTable table;

  public Program compile(char[] p_input) {
    //error = new CompilerError(null); //COMENTADO PARA COMPILAR
    lexer = new Lexer(p_input);
    table = new SymbolTable();
    // error.setLexer(lexer);

    input = p_input;
    tokenPos = 0;

    // symbolTable = new Hashtable();

    lexer.nextToken();

    Program e = program();
    if (tokenPos != input.length)
      error("Fim de codigo esperado!");

    return e;
  }

  // Program ::= Func {Func}
  private Program program() {
    System.out.println("Entrou na funcao program: " + lexer.token);
    Func esq = func();
    Func dir = func();

    ArrayList<Func> f = new ArrayList<Func>();
    f.add(esq);
    f.add(dir);

    return new Program(f);
  }

  private void RelOp() {
     System.out.println("Entrou na funcao RelOP " + lexer.token);
    if (lexer.token == Symbol.EQUAL || lexer.token == Symbol.LT || lexer.token == Symbol.LTE || lexer.token == Symbol.GT
        || lexer.token == Symbol.GTE) {

    } else {
      System.out.println("Operador não reconhecido");
    }
    lexer.nextToken();
  }

  // ReturnStat ::= "return" Expr ";"
  public ReturnStat ReturnStat() {
     System.out.println("Entrou na funcao ReturnStat " + lexer.token);

    if (lexer.token != Symbol.RETURN) {
      System.out.println("Expected 'Return' but found '" + lexer.getStringValue() + "'");
    }

    lexer.nextToken();
    // lexer.nextToken();

    Expr expr = expr();


    if (lexer.token != Symbol.SEMICOLON) {
      System.out.println(lexer.getCurrentLine());
      System.out.println("Expected ';' but found '" + lexer.getStringValue() + "'");
    }

    lexer.nextToken();

    return new ReturnStat(expr);
  }

  //Stat ::= AssignExprStat | ReturnStat | VarDecStat | IfStat | WhileStat
  private Stat stat() {
     System.out.println("Entrou na funcao stat " + lexer.token);
    switch (lexer.token) {
    case IDLITERAL:
      return assignExprStat();
    case RETURN:
      return ReturnStat();
    case VAR:
      return varDecStat();
    case IF:
      return ifStat();
    case WHILE:
      return whileStat();
    default:
      // will never be executed
      System.out.println("Statement expected");
      System.out.println(lexer.token);
    }
    return null;
  }

  // StatList ::= "{"{Stat}"}"
  private StatList statList() {
    System.out.println("Entrou na funcao statList " + lexer.token);
    ArrayList<Stat> v = new ArrayList<Stat>();

    if(lexer.token != Symbol.LBRA){
      System.out.println("Esperado {");
    }
    lexer.nextToken();
    stat();

    System.out.println("AOOOO22222 " + lexer.token);
 
    if(lexer.token != Symbol.RBRA){
      System.out.println("Esperado }, encontrou " + lexer.token);
      System.out.println(lexer.getCurrentLine());
    }

    return new StatList(v);
  }

  //IfStat ::= "if" Expr StatList [ "else" StatList ]
  private IfStat ifStat() {
     System.out.println("Entrou na funcao ifStat " + lexer.token);

    if (lexer.token != Symbol.IF) {
      System.out.println("'if' expected");
    }

    lexer.nextToken();

    // System.out.println("TOKEN " + lexer.token);

    Expr e = expr();

    lexer.nextToken();

    // devemos checar pela abertura e fechamento de chaves?

    if (lexer.token != Symbol.LBRA) {
      System.out.println("{ expected");
    }

    StatList ifPart = statList();
    StatList elsePart = null;

    if (lexer.token != Symbol.RBRA) {
      System.out.println("} expected: " + lexer.token);
    }

    lexer.nextToken();

    if (lexer.token == Symbol.ELSE) {
      lexer.nextToken();
      // System.out.println("TOKEN " + lexer.token);

      // if (lexer.token != Symbol.LBRA) {
      //   System.out.println("{ expected::::: " + lexer.token);
      // }

      // lexer.nextToken();

      // System.out.println("TOKEN2 " + lexer.token);
      elsePart = statList();
      // lexer.nextToken();

      // if (lexer.token != Symbol.RBRA) {
      //   System.out.println("} expected:");
      // }
    }

    lexer.nextToken(); // não sei se precisa

    return new IfStat(e, ifPart, elsePart);

  }

  // Type::="Int"|"Boolean"|"String"
  private Type type() {
     System.out.println("Entrou na funcao type " + lexer.token);
    Type result;

    if(lexer.token == Symbol.INT){
      System.out.println("int");
    }
    else if(lexer.token == Symbol.BOOLEAN){
      System.out.println("boolean");
    }
    else if(lexer.token == Symbol.STRING){
      System.out.println("String");
    }
    else{
      System.out.println("Tipo não reconhecido");
      System.out.println("TOken:" + lexer.token);
      return null;
    }

    result = new Type(lexer.token);
    lexer.nextToken();
    return result;
  }

  private VarDecStat varDecStat() {
     System.out.println("Entrou na funcao varDecStat " + lexer.token);

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

  //WhileStat ::= "while" Expr StatList
  private WhileStat whileStat() {
     System.out.println("Entrou na funcao whileStat " + lexer.token);
    if (lexer.token != Symbol.WHILE) {
      System.out.println("'while' expected");
    }

    lexer.nextToken();

    Expr e = expr();

    lexer.nextToken();

    StatList whilePart = statList();

    lexer.nextToken();

    return new WhileStat(whilePart, e);

  }

  public boolean literalBoolean() {
     System.out.println("Entrou na funcao literalBoolean " + lexer.token);

    if (lexer.token == Symbol.BOOLLITERAL) {
      boolean boo = lexer.getBoolValue();
      lexer.nextToken();
      return boo;
    } else {
      System.out.println("Error in the boolean type");
    }

    return false;
  }

  private ParamList paramList() {
     System.out.println("Entrou na funcao paramList " + lexer.token);
    // ParamList ::= ParamDec { ’,’ ParamDec }
   ParamDec p = paramDec();

   ArrayList<ParamDec> pList = new ArrayList<ParamDec>();
   ParamList paramList = new ParamList(pList);
   pList.add(p);

   while (lexer.token == Symbol.COMMA) {
      lexer.nextToken();
      p = paramDec();
      pList.add(p);
   }

   paramList.setArrayParam(pList);
   return paramList;
  }

  private ParamDec paramDec() {
     System.out.println("Entrou na funcao paramDec " + lexer.token);
    // ParamDec ::= Id ":" Type
    if (lexer.token != Symbol.IDLITERAL) {
      // error.signal("Identifier expected");
      System.out.println("identifier expected");
    }
    // name of the identifier
    String id = (String) lexer.getStringValue();
    lexer.nextToken();

    if (lexer.token != Symbol.COLON) { // :
      System.out.println(": expected");
      // error.show(": expected");
    } else {
      lexer.nextToken();
    }
    // get the type
    Type typeVar = type();
    ParamDec param = new ParamDec(id, typeVar);

    return param;
  }

  private void error(String errorMsg) {
     System.out.println("Entrou na funcao error " + lexer.token);
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
     System.out.println("Entrou na funcao exprAnd " + lexer.token);
    Expr esq, dir;
    esq = exprRel();

    if (lexer.token == Symbol.AND) {
      lexer.nextToken();
      dir = exprRel();
      esq = new ExprAnd(esq, Symbol.AND, dir);
    }
    //  else
    //   System.out.println("'and' expected");

    return esq;
  }

   //  ::= LiteralInt | LiteralBoolean | LiteralString
   public Expr exprLiteral() {
     System.out.println("Entrou na funcao exprLiteral " + lexer.token);
       Symbol op = lexer.token;

       switch(op){
       case INTLITERAL:
         lexer.nextToken();
         break;

        case BOOLLITERAL:
          lexer.nextToken();
          break;

        case STRINGLITERAL:
         lexer.nextToken();
         break;
       }

      return new ExprLiteral(op);
   }

  // Expr ::= ExprAnd {”or”ExprAnd}
  public Expr expr() {
     System.out.println("Entrou na funcao expr " + lexer.token);
    Expr esq, dir;
    esq = exprAnd();
    Symbol op;

    while ((op = lexer.token) == Symbol.OR) {
      lexer.nextToken();
      dir = exprAnd();
    }
    return esq;
  }

  //ExprMult ::= ExprUnary {(” ∗ ” | ”/”)ExprUnary}
  private Expr exprMult() {
     System.out.println("Entrou na funcao exprMult " + lexer.token);
    Symbol op;
    Expr esq, dir;
    esq = exprUnary();

    while ((op = lexer.token) == Symbol.MULT || op == Symbol.DIV) {
      System.out.println("AOOOOO BOBINA: " + lexer.token);
      lexer.nextToken();
      dir = exprUnary();
      esq = new ExprMult(esq, op, dir);
    }
    return esq;
  }

  // ExprAdd ::= ExprMult {(” + ” | ” − ”)ExprMult}
  private Expr exprAdd() {
    System.out.println("Entrou na funcao exprAdd " + lexer.token);
    Symbol op;
    Expr esq, dir;
    esq = exprMult();

    while ((op = lexer.token) == Symbol.PLUS || op == Symbol.MINUS) {
      System.out.println("OP " + op);
      lexer.nextToken();
      dir = exprMult();
      esq = new ExprAdd(esq, op, dir);
    }
    return esq;
  }

  // AssignExprStat ::= Expr [ "=" Expr ] ";"
  public Stat assignExprStat() {
     System.out.println("Entrou na funcao assignExprStat " + lexer.token);
    Expr esq, dir;
    esq = expr();
    dir = null;

    if (lexer.token == Symbol.ASSIGN) {
      lexer.nextToken();
      dir = expr();
      lexer.nextToken();
      if (lexer.token == Symbol.SEMICOLON)
        lexer.nextToken();
    }

    else
      System.out.println("simbolo incorreto " + lexer.token);

    return new AssignExprStat(esq, dir);
  }

  private Expr exprRel() {
     System.out.println("Entrou na funcao exprRel " + lexer.token);
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
     System.out.println("Entrou na funcao exprUnary " + lexer.token);
    // ExprUnary ::= [ ( "+" | "-" ) ] ExprPrimary
    Symbol op = lexer.token;
    if (op == Symbol.PLUS || op == Symbol.MINUS)
      lexer.nextToken();

    Expr e = exprPrimary();

    return new ExprUnary(e, op);
  }

   // ExprPrimary ::= Id | FuncCall | ExprLiteral
   private Expr exprPrimary() {
      System.out.println("Entrou na funcao exprPrimary " + lexer.getStringValue());
      // String value = lexer.getStringValue()
      if(lexer.nextNoSkip() == '(') {
        // lexer.nextToken();
        return funcCall();
      }
      else if(lexer.token == Symbol.IDLITERAL){
        Variable id = new Variable(lexer.token.toString());
        lexer.nextToken();
        return id;
      }
      else {
        // lexer.nextToken();
        return exprLiteral();
      }
  }

  private Func func() {
     System.out.println("Entrou na funcao func " + lexer.token);
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

    lexer.nextToken();

    ParamList p = null;
    if (lexer.token == Symbol.LPAR) {
      lexer.nextToken();
      p = paramList();
      if (lexer.token != Symbol.RPAR) {
        System.out.println(") expected");
      } else{
        lexer.nextToken();
      }
    }

    Type t = null;
    if (lexer.token == Symbol.ARROW) {
      lexer.nextToken();
      System.out.println(lexer.token);
      t = type();
    }

    statList();

    return new Func(t.getType(), name, p);
  }

  private Expr funcCall() {
     System.out.println("Entrou na funcao funcCall " + lexer.token);
    // FuncCall ::= Id "(" [ Expr {”, ”Expr} ] ")"
    if (lexer.token != Symbol.IDLITERAL)
      System.out.println("Identifier expected");

    lexer.nextToken();
    String name = (String) lexer.getStringValue();

    ArrayList<Expr> eList = new ArrayList<Expr>();

    if (lexer.token != Symbol.LPAR) {
      System.out.println("( expected");
    } else
      lexer.nextToken();
    if (lexer.token != Symbol.RPAR) {
      Expr e = expr();
      eList.add(e);
      // lexer.nextToken();
      while (lexer.token == Symbol.COMMA) {
        lexer.nextToken(); // space
        e = expr();
        eList.add(e);
      }
      
      if (lexer.token != Symbol.RPAR) {
        System.out.println(") expected");
      }
      lexer.nextToken();
    }
    return new FuncCall(name, eList);
  }

  private char token;
  private int tokenPos;
  private char[] input;
  // private Hashtable<Character, VarDecStat> symbolTable;
}
