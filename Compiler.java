//     Nome                    RA
// Bruno Asti Baradel      726499
// Pablo Laranjo           726577
// Pedro Coelho            743585
// Vinícius Crepschi       743601

import AST.*;
import Lexer.*;
import Error.*;
import java.util.ArrayList;

public class Compiler {
  private Lexer lexer;
  private SymbolTable table;
  private CompilerError error;

///////////////////////////////////////////////////////////////////////////////////////////

  public Program compile(char[] p_input, PW pw, String fileName) {
    RuntimeException exception = new RuntimeException();

    lexer = new Lexer(p_input);
    table = new SymbolTable();
    error = new CompilerError(pw, fileName);
    error.setLexer(lexer);

    //algumas inicializações
    input = p_input;
    tokenPos = 0;

    //funções pre-definidas na linguagem
    Expr expression = null;
    Type tipo = null;

    tipo = new Type(Symbol.INTLITERAL);
    table.putFunction("readInt", new Func("readInt", tipo));

    tipo = new Type(Symbol.STRINGLITERAL);
    table.putFunction("readString", new Func("readString", tipo));

    // table.putFunction("print", new Func("print", expression));
    // table.putFunction("println", new Func("println", expression));
    table.putFunction("writeln", new Func("writeln", expression));
    table.putFunction("write", new Func("write", expression));
    // symbolTable = new Hashtable();

    lexer.nextToken();

    Program e = program();
    if (lexer.token != Symbol.EOF)
      error.message("EOF expected");

    return e;
  }

///////////////////////////////////////////////////////////////////////////////////////////

  // Program ::= Func {Func}
  private Program program() {
    //System.out.println("Entrou na funcao program: " + lexer.token);
    ArrayList<Func> f = new ArrayList<Func>();

    while (lexer.token == Symbol.FUNCTION) {
      f.add(func());
      table.resetLocal();
      lexer.nextToken();
      // System.out.println("ACABOU A FUNC" + lexer.token);
    }

    //System.out.println("PRINT NA PROGRAM" + lexer.token);

    Program program = new Program(f);

    lexer.nextToken();

    if (lexer.token != Symbol.EOF){
      // System.out.println("ULTIMO TOKEN: " + lexer.token);
      if(lexer.token == Symbol.IDLITERAL || lexer.token == Symbol.STRINGLITERAL){
        error.message("EOF expected and found: " + lexer.getStringValue());
      }
      else if (lexer.token == Symbol.INTLITERAL){
        error.message("EOF expected and found: " + lexer.getIntValue());
      }
      else if (lexer.token == Symbol.BOOLLITERAL) {
        error.message("EOF expected and found: " + lexer.getBoolValue());
      }
      else{
        error.message("EOF expected and found: " + lexer.token);
      }
    }

    /* verifica se existe uma funcao main */
    if(table.returnFunction("main") == null)
       System.out.println("Function 'main' expected but not found");
    return program;
  }

///////////////////////////////////////////////////////////////////////////////////////////

  //RelOp ::= "<" | "<=" | ">" | ">=" | "==" | "!="
  private void RelOp() {
     //System.out.println("Entrou na funcao RelOP " + lexer.token);
    if (lexer.token == Symbol.EQUAL || lexer.token == Symbol.LT || lexer.token == Symbol.LTE || lexer.token == Symbol.GT
        || lexer.token == Symbol.GTE) {

    } else {
      if (lexer.token == Symbol.IDLITERAL || lexer.token == Symbol.STRINGLITERAL) {
        error.message("Operator expected and found: " + lexer.getStringValue());
      } else if (lexer.token == Symbol.INTLITERAL) {
        error.message("Operator expected and found: " + lexer.getIntValue());
      } else if (lexer.token == Symbol.BOOLLITERAL) {
        error.message("Operator expected and found: " + lexer.getBoolValue());
      } else {
        error.message("Operator expected and found: " + lexer.token);
      }
    }

    lexer.nextToken();
  }

///////////////////////////////////////////////////////////////////////////////////////////

  // ReturnStat ::= "return" Expr ";"
  public ReturnStat ReturnStat() {

    if (lexer.token != Symbol.RETURN) {
      if (lexer.token == Symbol.IDLITERAL || lexer.token == Symbol.STRINGLITERAL) {
        error.message("Expected 'Return' but found: " + lexer.getStringValue());
      } else if (lexer.token == Symbol.INTLITERAL) {
        error.message("Expected 'Return' but found: " + lexer.getIntValue());
      } else if (lexer.token == Symbol.BOOLLITERAL) {
        error.message("Expected 'Return' but found: " + lexer.getBoolValue());
      } else {
        error.message("Expected 'Return' but found: " + lexer.token);
      }
    }

    lexer.nextToken();

    if(lexer.token == Symbol.SEMICOLON){
      error.message("Return without expression");
      Expr expr = expr();
      return new ReturnStat(expr);
    }
    else{
      Expr expr2 = expr();

      if (lexer.token != Symbol.SEMICOLON) {
        if (lexer.token == Symbol.IDLITERAL || lexer.token == Symbol.STRINGLITERAL) {
          error.message("Expected ';' but found: " + lexer.getStringValue());
        } else if (lexer.token == Symbol.INTLITERAL) {
          error.message("Expected ';' but found: " + lexer.getIntValue());
        } else if (lexer.token == Symbol.BOOLLITERAL) {
          error.message("Expected ';' but found: " + lexer.getBoolValue());
        } else {
          error.message("Expected ';' but found: " + lexer.token);
        }
      }

      lexer.nextToken();

      return new ReturnStat(expr2);
    }
  }

///////////////////////////////////////////////////////////////////////////////////////////

  //Stat ::= AssignExprStat | ReturnStat | VarDecStat | IfStat | WhileStat
  private Stat stat() {
     //System.out.println("Entrou na funcao stat " + lexer.token);
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
    case INTLITERAL:
      return assignExprStat();

    case BOOLLITERAL:
      return assignExprStat();

    case STRINGLITERAL:
      return assignExprStat();

    default:
      // will never be executed
      // System.out.println("Statement expected");
      // System.out.println(lexer.token);
      return null;
    }
  }

///////////////////////////////////////////////////////////////////////////////////////////

  // StatList ::= "{"{Stat}"}"
  private StatList statList() {
    //System.out.println("Entrou na funcao statList " + lexer.token);
    ArrayList<Stat> v = new ArrayList<Stat>();

    if(lexer.token != Symbol.LBRA){
      if (lexer.token == Symbol.IDLITERAL || lexer.token == Symbol.STRINGLITERAL) {
        error.message("Esperado {, encontrou:  " + lexer.getStringValue());
      } else if (lexer.token == Symbol.INTLITERAL) {
        error.message("Esperado {, encontrou:  " + lexer.getIntValue());
      } else if (lexer.token == Symbol.BOOLLITERAL) {
        error.message("Esperado {, encontrou:  " + lexer.getBoolValue());
      } else {
        error.message("Esperado {, encontrou:  " + lexer.token);
      }
    }
    lexer.nextToken();

    Stat a;
    while(true) {
      a = stat();

      if(a == null) {
        break;
      }
      else {
        v.add(a);
      }
    }

    if(lexer.token != Symbol.RBRA) {
      if (lexer.token == Symbol.IDLITERAL || lexer.token == Symbol.STRINGLITERAL) {
        error.message("Esperado }, encontrou   " + lexer.getStringValue());
      } else if (lexer.token == Symbol.INTLITERAL) {
        error.message("Esperado }, encontrou   " + lexer.getIntValue());
      } else if (lexer.token == Symbol.BOOLLITERAL) {
        error.message("Esperado }, encontrou   " + lexer.getBoolValue());
      } else {
        error.message("Esperado }, encontrou   " + lexer.token);
      }
      // System.out.println("LINHA: " + lexer.getCurrentLine());
    }

    return new StatList(v);
  }

///////////////////////////////////////////////////////////////////////////////////////////

  //IfStat ::= "if" Expr StatList [ "else" StatList ]
  private IfStat ifStat() {
     //System.out.println("Entrou na funcao ifStat " + lexer.token);

    if (lexer.token != Symbol.IF) {
      if (lexer.token == Symbol.IDLITERAL || lexer.token == Symbol.STRINGLITERAL) {
        error.message("'if' expected, but found: " + lexer.getStringValue());
      } else if (lexer.token == Symbol.INTLITERAL) {
        error.message("'if' expected, but found: " + lexer.getIntValue());
      } else if (lexer.token == Symbol.BOOLLITERAL) {
        error.message("'if' expected, but found: " + lexer.getBoolValue());
      } else {
        error.message("'if' expected, but found: " + lexer.token);
      }
    }

    lexer.nextToken();

    Expr e = expr();

    if (lexer.token != Symbol.LBRA) {
      if (lexer.token == Symbol.IDLITERAL || lexer.token == Symbol.STRINGLITERAL) {
        error.message("{ expected and found " + lexer.getStringValue());
      } else if (lexer.token == Symbol.INTLITERAL) {
        error.message("{ expected and found " + lexer.getIntValue());
      } else if (lexer.token == Symbol.BOOLLITERAL) {
        error.message("{ expected and found " + lexer.getBoolValue());
      } else {
        error.message("{ expected and found " + lexer.token);
      }
      // System.out.println("LINE: " + lexer.getCurrentLine());
    }

    StatList ifPart = statList();
    StatList elsePart = null;

    if (lexer.token != Symbol.RBRA) {
      if (lexer.token == Symbol.IDLITERAL || lexer.token == Symbol.STRINGLITERAL) {
        error.message("} expected and found: " + lexer.getStringValue());
      } else if (lexer.token == Symbol.INTLITERAL) {
        error.message("} expected and found: " + lexer.getIntValue());
      } else if (lexer.token == Symbol.BOOLLITERAL) {
        error.message("} expected and found: " + lexer.getBoolValue());
      } else {
        error.message("} expected and found: " + lexer.token);
      }
    }

    lexer.nextToken();

    if (lexer.token == Symbol.ELSE) {
      lexer.nextToken();
      elsePart = statList();
      lexer.nextToken();
    }

    // lexer.nextToken();

    return new IfStat(e, ifPart, elsePart);

  }

///////////////////////////////////////////////////////////////////////////////////////////

  // Type::="Int"|"Boolean"|"String"
  private Type type() {
     //System.out.println("Entrou na funcao type " + lexer.token);
    Type result;

    if(lexer.token == Symbol.INT){
      // System.out.println("int");
    }
    else if(lexer.token == Symbol.BOOLEAN){
      // System.out.println("boolean");
    }
    else if(lexer.token == Symbol.STRING){
      // System.out.println("String");
    }
    else{
      if (lexer.token == Symbol.IDLITERAL || lexer.token == Symbol.STRINGLITERAL) {
        error.message("Tipo não reconhecido " + lexer.getStringValue());
      } else if (lexer.token == Symbol.INTLITERAL) {
        error.message("Tipo não reconhecido " + lexer.getIntValue());
      } else if (lexer.token == Symbol.BOOLLITERAL) {
        error.message("Tipo não reconhecido " + lexer.getBoolValue());
      } else {
        error.message("Tipo não reconhecido " + lexer.token);
      }
      //System.out.println("Token:" + lexer.token);
      return null;
    }

    result = new Type(lexer.token);
    lexer.nextToken();
    return result;
  }

///////////////////////////////////////////////////////////////////////////////////////////

  private VarDecStat varDecStat() {
     //System.out.println("Entrou na funcao varDecStat " + lexer.token);

    if (lexer.token != Symbol.VAR){
      if (lexer.token == Symbol.IDLITERAL || lexer.token == Symbol.STRINGLITERAL) {
        error.message("Identifier expected and found " + lexer.getStringValue());
      } else if (lexer.token == Symbol.INTLITERAL) {
        error.message("Identifier expected and found " + lexer.getIntValue());
      } else if (lexer.token == Symbol.BOOLLITERAL) {
        error.message("Identifier expected and found " + lexer.getBoolValue());
      } else {
        error.message("Identifier expected and found " + lexer.token);
      }
    }

    lexer.nextToken();

    // System.out.println("Identifier: " + lexer.getStringValue());

    // name of the identifier
    String name = lexer.getStringValue();

    if (lexer.token == Symbol.INT || lexer.token == Symbol.STRING || lexer.token == Symbol.BOOLEAN) {
      error.message(": Variável nomeada com palavra reservada " + lexer.getStringValue());
    }

    lexer.nextToken();

    if (lexer.token != Symbol.COLON) {
      if (lexer.token == Symbol.IDLITERAL || lexer.token == Symbol.STRINGLITERAL) {
        error.message(": expected and found " + lexer.getStringValue());
      } else if (lexer.token == Symbol.INTLITERAL) {
        error.message(": expected and found " + lexer.getIntValue());
      } else if (lexer.token == Symbol.BOOLLITERAL) {
        error.message(": expected and found " + lexer.getBoolValue());
      } else {
        error.message(": expected and found " + lexer.token);
      }
    }

    lexer.nextToken();

    Type typeVar = type();

    if (lexer.token != Symbol.SEMICOLON) {
      if (lexer.token == Symbol.IDLITERAL || lexer.token == Symbol.STRINGLITERAL) {
        error.message("; expected and found " + lexer.getStringValue());
      } else if (lexer.token == Symbol.INTLITERAL) {
        error.message("; expected and found " + lexer.getIntValue());
      } else if (lexer.token == Symbol.BOOLLITERAL) {
        error.message("; expected and found " + lexer.getBoolValue());
      } else {
        error.message("; expected and found " + lexer.token);
      }
    }

    lexer.nextToken();

    VarDecStat v = null;

    if(table.returnLocal(name) != null) {
      error.message("Variável já declarada: " + name);
      v = (VarDecStat) table.returnLocal(name);
    }

    else{
      v = new VarDecStat(name, typeVar);
      table.putLocal(name, v);
    }

    return v;
  }

///////////////////////////////////////////////////////////////////////////////////////////

  //WhileStat ::= "while" Expr StatList
  private WhileStat whileStat() {
     //System.out.println("Entrou na funcao whileStat " + lexer.token);
    if (lexer.token != Symbol.WHILE) {
      if (lexer.token == Symbol.IDLITERAL || lexer.token == Symbol.STRINGLITERAL) {
        error.message("'while' expected and found " + lexer.getStringValue());
      } else if (lexer.token == Symbol.INTLITERAL) {
        error.message("'while' expected and found " + lexer.getIntValue());
      } else if (lexer.token == Symbol.BOOLLITERAL) {
        error.message("'while' expected and found " + lexer.getBoolValue());
      } else {
        error.message("'while' expected and found " + lexer.token);
      }
    }

    lexer.nextToken();

    Expr e = expr();

    // lexer.nextToken();

    StatList whilePart = statList();

    lexer.nextToken();

    return new WhileStat(whilePart, e);
  }

///////////////////////////////////////////////////////////////////////////////////////////

  public boolean literalBoolean() {
     //System.out.println("Entrou na funcao literalBoolean " + lexer.token);

    if (lexer.token == Symbol.BOOLLITERAL) {
      boolean boo = lexer.getBoolValue();
      lexer.nextToken();
      return boo;
    } else {
      if (lexer.token == Symbol.IDLITERAL || lexer.token == Symbol.STRINGLITERAL) {
        error.message("Error in the boolean type, at " + lexer.getStringValue());
      } else if (lexer.token == Symbol.INTLITERAL) {
        error.message("Error in the boolean type, at " + lexer.getIntValue());
      } else if (lexer.token == Symbol.BOOLLITERAL) {
        error.message("Error in the boolean type, at " + lexer.getBoolValue());
      } else {
        error.message("Error in the boolean type, at " + lexer.token);
      }
    }
    // lexer.nextToken();
    return false;
  }

///////////////////////////////////////////////////////////////////////////////////////////

  private ParamList paramList() {
     //System.out.println("Entrou na funcao paramList " + lexer.token);
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

///////////////////////////////////////////////////////////////////////////////////////////

  private ParamDec paramDec() {
     //System.out.println("Entrou na funcao paramDec " + lexer.token);
    // ParamDec ::= Id ":" Type
    if (lexer.token != Symbol.IDLITERAL) {
      if (lexer.token == Symbol.IDLITERAL || lexer.token == Symbol.STRINGLITERAL) {
        error.message("identifier expected and found: " + lexer.getStringValue());
      } else if (lexer.token == Symbol.INTLITERAL) {
        error.message("identifier expected and found: " + lexer.getIntValue());
      } else if (lexer.token == Symbol.BOOLLITERAL) {
        error.message("identifier expected and found: " + lexer.getBoolValue());
      } else {
        error.message("identifier expected and found: " + lexer.token);
      }
    }
    // name of the identifier
    String id = (String) lexer.getStringValue();
    lexer.nextToken();

    if (lexer.token != Symbol.COLON) { // :
      if (lexer.token == Symbol.IDLITERAL || lexer.token == Symbol.STRINGLITERAL) {
        error.message(": expected and found " + lexer.getStringValue());
      } else if (lexer.token == Symbol.INTLITERAL) {
        error.message(": expected and found " + lexer.getIntValue());
      } else if (lexer.token == Symbol.BOOLLITERAL) {
        error.message(": expected and found " + lexer.getBoolValue());
      } else {
        error.message(": expected and found " + lexer.token);
      }
      // error.show(": expected");
    } else {
      lexer.nextToken();
    }

    // get the type
    Type typeVar = type();

    if(table.returnLocal(id) != null){
      error.message("Parâmetro " + id + " já foi declarado anteriormente");
    }
    else{
      table.putLocal(id, new VarDecStat(id, typeVar));
    }

    ParamDec param = new ParamDec(id, typeVar);

    return param;
  }

///////////////////////////////////////////////////////////////////////////////////////////

  // ExprAnd::= ExprRel {”and” ExprRel}
  private ExprAnd exprAnd() {
     //System.out.println("Entrou na funcao exprAnd " + lexer.token);
    ExprRel esq, dir;
    ArrayList<Expr> expr = new ArrayList<Expr>();
    Type tipoEsq, tipoDir;

    esq = exprRel();

    dir = null;
    tipoEsq = esq.getType();
    tipoDir = null;
    expr.add(esq);

    while (lexer.token == Symbol.AND) {
      lexer.nextToken();
      dir = exprRel();
      tipoDir = dir.getType();
      expr.add(dir);

      if (tipoEsq.getType() != tipoDir.getType()) {
        if (!((tipoEsq.getType() == Symbol.BOOLEAN && tipoDir.getType() == Symbol.BOOLLITERAL)
            || (tipoEsq.getType() == Symbol.BOOLLITERAL && tipoDir.getType() == Symbol.BOOLEAN))) {

          error.message("Operação AND com tipos não booleanos");
        }
      }
    }

    return new ExprAnd(expr, Symbol.AND, tipoEsq);
  }

///////////////////////////////////////////////////////////////////////////////////////////

   //ExprLiteral  ::= LiteralInt | LiteralBoolean | LiteralString
   public Expr exprLiteral() {
     //System.out.println("Entrou na funcao exprLiteral " + lexer.token);
       Symbol op = lexer.token;
       Type tipo = null;
       String value = "";
       int valueInt = 0;

       switch(op){
       case INTLITERAL:
         lexer.nextToken();
         tipo = new Type(Symbol.INTLITERAL);
         valueInt = lexer.getIntValue();
         break;

        case TRUE:
          lexer.nextToken();
          tipo = new Type(Symbol.BOOLLITERAL);
          value = "true";
          break;

        case FALSE:
          lexer.nextToken();
          tipo = new Type(Symbol.BOOLLITERAL);
          value = "false";
          break;

        case STRINGLITERAL:
          tipo = new Type(Symbol.STRINGLITERAL);
          lexer.nextToken();
          value = lexer.getStringValue();
          break;

         default:
         lexer.nextToken();
          if (lexer.token == Symbol.IDLITERAL || lexer.token == Symbol.STRINGLITERAL) {
            error.message("Invalid Expression at: " + lexer.getStringValue());
          } else if (lexer.token == Symbol.INTLITERAL) {
            error.message("Invalid Expression at: " + lexer.getIntValue());
          } else if (lexer.token == Symbol.BOOLLITERAL) {
            error.message("Invalid Expression at: " + lexer.getBoolValue());
          } else {
            error.message("Invalid Expression at: " + lexer.token);
          }
       }

      return new ExprLiteral(value, op, tipo);
   }

///////////////////////////////////////////////////////////////////////////////////////////

  // Expr ::= ExprAnd {”or” ExprAnd}
  public Expr expr() {
    ExprAnd esq, dir;
    Symbol op;
    ArrayList<Expr> expr = new ArrayList<Expr>();
    Type tipoEsq, tipoDir;

    esq = exprAnd();
    dir = null;
    tipoEsq = esq.getType();
    tipoDir = null;

    expr.add(esq);

    while ((op = lexer.token) == Symbol.OR) {
      lexer.nextToken();
      dir = exprAnd();
      tipoDir = dir.getType();
      expr.add(dir);

      if (tipoEsq.getType() != tipoDir.getType()) {
        if (!((tipoEsq.getType() == Symbol.BOOLEAN && tipoDir.getType() == Symbol.BOOLLITERAL)
            || (tipoEsq.getType() == Symbol.BOOLLITERAL && tipoDir.getType() == Symbol.BOOLEAN))) {

          error.message("Operação OR com tipos não booleanos");
        }
      }
    }

    return new ExprAnd(expr, Symbol.OR, tipoEsq);
  }

///////////////////////////////////////////////////////////////////////////////////////////

  //ExprMult ::= ExprUnary {(” ∗ ” | ”/”)ExprUnary}
  private ExprMult exprMult() {
    Symbol op;
    ExprUnary esq, dir;
    ArrayList<ExprUnary> expr = new ArrayList<ExprUnary>();
    Type tipoEsq, tipoDir;

    esq = exprUnary();
    tipoEsq = esq.getType();
    tipoDir = null;

    expr.add(esq);

    while ((op = lexer.token) == Symbol.MULT || op == Symbol.DIV) {
      lexer.nextToken();
      dir = exprUnary();
      tipoDir = dir.getType();

      if(tipoEsq.getType() != tipoDir.getType()){
        if(!((tipoEsq.getType() == Symbol.INT && tipoDir.getType() == Symbol.INTLITERAL) ||
          (tipoEsq.getType() == Symbol.INTLITERAL && tipoDir.getType() == Symbol.INT) ||
          (tipoEsq.getType() == Symbol.STRING && tipoDir.getType() == Symbol.STRINGLITERAL) ||
          (tipoEsq.getType() == Symbol.STRINGLITERAL && tipoDir.getType() == Symbol.STRING) ||
          (tipoEsq.getType() == Symbol.BOOLEAN && tipoDir.getType() == Symbol.BOOLLITERAL) ||
          (tipoEsq.getType() == Symbol.BOOLLITERAL && tipoDir.getType() == Symbol.BOOLEAN))){

            error.message("Multiplicação/Divisão com tipos de operandos distintos");
        }
      }
      expr.add(dir);
    }
    return new ExprMult(expr, op, tipoEsq);
  }

///////////////////////////////////////////////////////////////////////////////////////////

  // ExprAdd ::= ExprMult {(” + ” | ” − ”)ExprMult}
  private ExprAdd exprAdd() {
    Symbol op;
    ExprMult esq, dir;
    ArrayList<ExprMult> expr = new ArrayList<ExprMult>();
    Type tipoEsq, tipoDir;

    esq = exprMult();
    tipoEsq = esq.getType();
    tipoDir = null;
    expr.add(esq);

    while ((op = lexer.token) == Symbol.PLUS || op == Symbol.MINUS) {
      lexer.nextToken();
      dir = exprMult();
      tipoDir = dir.getType();
      expr.add(dir);
    }

    if(tipoDir != null){
      if(tipoEsq.getType() != tipoDir.getType()){
          if(!((tipoEsq.getType() == Symbol.INT && tipoDir.getType() == Symbol.INTLITERAL) ||
            (tipoEsq.getType() == Symbol.INTLITERAL && tipoDir.getType() == Symbol.INT) ||
            (tipoEsq.getType() == Symbol.STRING && tipoDir.getType() == Symbol.STRINGLITERAL) ||
            (tipoEsq.getType() == Symbol.STRINGLITERAL && tipoDir.getType() == Symbol.STRING) ||
            (tipoEsq.getType() == Symbol.BOOLEAN && tipoDir.getType() == Symbol.BOOLLITERAL) ||
            (tipoEsq.getType() == Symbol.BOOLLITERAL && tipoDir.getType() == Symbol.BOOLEAN))){

              error.message("Soma com tipos de operandos incompatíveis");
          }
      }
    }

    return new ExprAdd(expr, op, tipoEsq);
  }

///////////////////////////////////////////////////////////////////////////////////////////

  // AssignExprStat ::= Expr [ "=" Expr ] ";"
  public Stat assignExprStat() {
     //System.out.println("Entrou na funcao assignExprStat " + lexer.token);
    Expr esq, dir;
    Type tipoEsq, tipoDir;
    int flag = 0;

    esq = expr();
    dir = null;
    tipoDir = null;
    tipoEsq = esq.getType();

    if (lexer.token == Symbol.ASSIGN) {
      lexer.nextToken();
      dir = expr();
      tipoDir = dir.getType();
      // lexer.nextToken();

      if (lexer.token == Symbol.SEMICOLON)
        flag = 1;
        // lexer.nextToken();
    }

    else if (lexer.token == Symbol.SEMICOLON) {
      flag = 1;
      // lexer.nextToken();
    }

    else {
      if (lexer.token == Symbol.IDLITERAL || lexer.token == Symbol.STRINGLITERAL) {
        error.message("Expected ';' but found: " + lexer.getStringValue());
      } else if (lexer.token == Symbol.INTLITERAL) {
        error.message("Expected ';' but found: " + lexer.getIntValue());
      } else if (lexer.token == Symbol.BOOLLITERAL) {
        error.message("Expected ';' but found: " + lexer.getBoolValue());
      } else {
        error.message("Expected ';' but found: " + lexer.token);
      }
    }

    if(tipoDir != null){
      //checando se algum literal está recebendo alguma atribuição
      if(tipoEsq.getType() == Symbol.BOOLLITERAL || tipoEsq.getType() == Symbol.INTLITERAL ||
         tipoEsq.getType() == Symbol.STRINGLITERAL){
           error.message("Literais não podem receber atribuições");
      }
      else if(tipoEsq.getType() != tipoDir.getType()){
          if(!((tipoEsq.getType() == Symbol.INT && tipoDir.getType() == Symbol.INTLITERAL) ||
            (tipoEsq.getType() == Symbol.STRING && tipoDir.getType() == Symbol.STRINGLITERAL) ||
            (tipoEsq.getType() == Symbol.BOOLEAN && tipoDir.getType() == Symbol.BOOLLITERAL))){

            error.message("Atribuição com tipos de operandos incompatíveis");
          }
      }
    }

    if(flag == 1){
      lexer.nextToken();
    }

    return new AssignExprStat(esq, dir);
  }

///////////////////////////////////////////////////////////////////////////////////////////

  // ExprRel ::= ExprAdd [ RelOp ExprAdd ]
  private ExprRel exprRel() {
    ExprAdd left, right;
    Type tipo, tipoEsq, tipoDir;

    left = exprAdd();
    tipoEsq = left.getType();
    tipoDir = null;
    right = null;
    tipo = left.getType();

    Symbol op = lexer.token;

    if (op == Symbol.EQUAL || op == Symbol.DIFFERENT || op == Symbol.LTE || op == Symbol.LT || op == Symbol.GTE
        || op == Symbol.GT) {
      lexer.nextToken();
      right = exprAdd();
      tipoDir = right.getType();
    }

    if(tipoDir != null){
      if(tipoEsq.getType() != tipoDir.getType()){
          if(!((tipoEsq.getType() == Symbol.INT && tipoDir.getType() == Symbol.INTLITERAL) ||
            (tipoEsq.getType() == Symbol.INTLITERAL && tipoDir.getType() == Symbol.INT) ||
            (tipoEsq.getType() == Symbol.STRING && tipoDir.getType() == Symbol.STRINGLITERAL) ||
            (tipoEsq.getType() == Symbol.STRINGLITERAL && tipoDir.getType() == Symbol.STRING) ||
            (tipoEsq.getType() == Symbol.BOOLEAN && tipoDir.getType() == Symbol.BOOLLITERAL) ||
            (tipoEsq.getType() == Symbol.BOOLLITERAL && tipoDir.getType() == Symbol.BOOLEAN))){

              error.message("Comparação com tipos de operandos incompatíveis");
          }
      }
    }

    return new ExprRel(left, right, op, tipo);
  }

///////////////////////////////////////////////////////////////////////////////////////////

  // ExprUnary ::= [ ( "+" | "-" ) ] ExprPrimary
  private ExprUnary exprUnary() {
    Symbol op = lexer.token;
    Type tipo;

    if (op == Symbol.PLUS || op == Symbol.MINUS)
      lexer.nextToken();

    Expr e = exprPrimary();
    tipo = e.getType();

    return new ExprUnary(e, op, tipo);
  }

///////////////////////////////////////////////////////////////////////////////////////////

   // ExprPrimary ::= Id | FuncCall | ExprLiteral
  private Expr exprPrimary() {
    // String value = lexer.getStringValue()

    // if(lexer.nextNoSkip() == '(') {
      // // lexer.nextToken();
      // return funcCall();
      // }
      if (lexer.token == Symbol.IDLITERAL) {
        String id = lexer.getStringValue();
        lexer.nextToken();

        if (lexer.token == Symbol.LPAR) {
          return funcCall(id);
        }
        else {
          Variable variable = new Variable(id);
          VarDecStat var = (VarDecStat) table.returnLocal(id);

          if(var == null){
            error.message("Variável " + id + " não declarada");
            Type tipo = new Type(Symbol.INT);
            variable.setType(tipo);

            // System.exit(0);
          }

          else{
            variable.setType(var.getTipo());
          }

          return variable;
        }

      } else {

      return exprLiteral();
    }
  }

///////////////////////////////////////////////////////////////////////////////////////////

  // Func ::= "function" Id [ "(" ParamList ")" ] ["->" Type ] StatList
  private Func func() {

    if (lexer.token != Symbol.FUNCTION) {
      if (lexer.token == Symbol.IDLITERAL || lexer.token == Symbol.STRINGLITERAL) {
        error.message("Function header expected and found: " + lexer.getStringValue());
      } else if (lexer.token == Symbol.INTLITERAL) {
        error.message("Function header expected and found: " + lexer.getIntValue());
      } else if (lexer.token == Symbol.BOOLLITERAL) {
        error.message("Function header expected and found: " + lexer.getBoolValue());
      } else {
        error.message("Function header expected and found: " + lexer.token);
      }
      return null;
    }
    lexer.nextToken();

    if(lexer.token == Symbol.INT || lexer.token == Symbol.BOOLEAN || lexer.token == Symbol.STRING) {
      error.message(": Nome da função com palavra reservada ");
    }

    if (lexer.token != Symbol.IDLITERAL){
      if (lexer.token == Symbol.IDLITERAL || lexer.token == Symbol.STRINGLITERAL) {
        error.message("Identifier expected and found: " + lexer.getStringValue());
      } else if (lexer.token == Symbol.INTLITERAL) {
        error.message("Identifier expected and found: " + lexer.getIntValue());
      } else if (lexer.token == Symbol.BOOLLITERAL) {
        error.message("Identifier expected and found: " + lexer.getBoolValue());
      } else {
        error.message("Identifier expected and found: " + lexer.token);
      }
    }



    String name = (String) lexer.getStringValue();

    lexer.nextToken();

    //Semantica para main possuir parametros e return
    if(name.compareTo("main") == 0 && lexer.token != Symbol.LBRA)
      error.message("Funcao 'main' nao pode conter parametros ou tipo de retorno");

    ParamList p = null;

    if (lexer.token == Symbol.LPAR) {
      lexer.nextToken();
      p = paramList();

      if (lexer.token != Symbol.RPAR) {
        if (lexer.token == Symbol.IDLITERAL || lexer.token == Symbol.STRINGLITERAL) {
          error.message(") expected and found: " + lexer.getStringValue());
        } else if (lexer.token == Symbol.INTLITERAL) {
          error.message(") expected and found: " + lexer.getIntValue());
        } else if (lexer.token == Symbol.BOOLLITERAL) {
          error.message(") expected and found: " + lexer.getBoolValue());
        } else {
          error.message(") expected and found: " + lexer.token);
        }
      }
      else{
        lexer.nextToken();
      }
    }

    Type t = null;
    if (lexer.token == Symbol.ARROW) {
      lexer.nextToken();
      t = type();
    }

    StatList statList = statList();

    //checagem se a função já havia sido declarada anteriormente
    if(table.returnFunction(name) != null){
      error.message("Função " + name + " já declarada");
    }

    //checagem se o retorno vindo de returnstat tem mesmo tipo que o retorno da função
    if(statList != null && t != null){
      ArrayList<Stat> list = statList.getList();

      if(list.get(list.size() - 1) instanceof ReturnStat){
        ReturnStat retorno = (ReturnStat) list.get(list.size() - 1);
        Type tipoRetorno = retorno.getExpr().getType();

        if(tipoRetorno.getType() != t.getType()){ //Bruno adicionou os .getType()
          if (!((tipoRetorno.getType() == Symbol.INT && t.getType() == Symbol.INTLITERAL)
              || (tipoRetorno.getType() == Symbol.INTLITERAL && t.getType() == Symbol.INT)
              || (tipoRetorno.getType() == Symbol.STRING && t.getType() == Symbol.STRINGLITERAL)
              || (tipoRetorno.getType() == Symbol.STRINGLITERAL && t.getType() == Symbol.STRING)
              || (tipoRetorno.getType() == Symbol.BOOLEAN && t.getType() == Symbol.BOOLLITERAL)
              || (tipoRetorno.getType() == Symbol.BOOLLITERAL && t.getType() == Symbol.BOOLEAN))) {

            error.message("Tipo de retorno da função difere do tipo declarado em seu header");
          }
        }
      }
    }

    //diferentes construtores para os diferentes tipos de função
    if(t == null) {
      if(p == null){
        table.putFunction(name, new Func(name));
        return new Func(name);
      }
      else{
        table.putFunction(name, new Func(name, p));
        return new Func(name, p);
      }
    }
    else if(p == null) {
      table.putFunction(name, new Func(name, t));
      return new Func(name, t);
    }
    else {
      table.putFunction(name, new Func(t, name, p));
      return new Func(t, name, p);
    }
  }

///////////////////////////////////////////////////////////////////////////////////////////

  // FuncCall ::= Id "(" [ Expr {”, ”Expr} ] ")"
  private Expr funcCall(String name) {
    Type tipo = null;
    ArrayList<Expr> eList = new ArrayList<Expr>();

    if (lexer.token != Symbol.LPAR) {
      if (lexer.token == Symbol.IDLITERAL || lexer.token == Symbol.STRINGLITERAL) {
        error.message("( expected and found: " + lexer.getStringValue());
      } else if (lexer.token == Symbol.INTLITERAL) {
        error.message("( expected and found: " + lexer.getIntValue());
      } else if (lexer.token == Symbol.BOOLLITERAL) {
        error.message("( expected and found: " + lexer.getBoolValue());
      } else {
        error.message("( expected and found: " + lexer.token);
      }
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
        if (lexer.token == Symbol.IDLITERAL || lexer.token == Symbol.STRINGLITERAL) {
          error.message(") expected and found: " + lexer.getStringValue());
        } else if (lexer.token == Symbol.INTLITERAL) {
          error.message(") expected and found: " + lexer.getIntValue());
        } else if (lexer.token == Symbol.BOOLLITERAL) {
          error.message(") expected and found: " + lexer.getBoolValue());
        } else {
          error.message(") expected and found: " + lexer.token);
        }
      }
      lexer.nextToken();
    }
    else{
      if (lexer.token != Symbol.RPAR) {
        if (lexer.token == Symbol.IDLITERAL || lexer.token == Symbol.STRINGLITERAL) {
          error.message(") expected and found: " + lexer.getStringValue());
        } else if (lexer.token == Symbol.INTLITERAL) {
          error.message(") expected and found: " + lexer.getIntValue());
        } else if (lexer.token == Symbol.BOOLLITERAL) {
          error.message(") expected and found: " + lexer.getBoolValue());
        } else {
          error.message(") expected and found: " + lexer.token);
        }
      }
      lexer.nextToken();
    }

    if(table.returnFunction(name) == null){
      error.message("Função " + name + " não declarada");
    }

    else{
      Func func = (Func) table.returnFunction(name);

      //caso chame a função write ou writeln e o numero de parametros for diferente de 1, apresenta erro
      if(name.equals("write") || name.equals("writeln")){
        if(eList.size() != 1){
          error.message("Chamada da função " + name + " deve possuir apenas 1 parâmetro");
        }
      }

      //checa se funcdec e funccall tem mesmo num de parametros
      else if(func.getParams() == null){
        if(eList.size() > 0){
          error.message("Chamada da função " + name + " com número diferente de parâmetros de sua declaração");
        }
      }

      else if(func.getParams().size() != eList.size()){
        error.message("Chamada da função "+ name + " com número diferente de parâmetros de sua declaração");
      }
      else{

        // Variable varList;
        // VarDecStat varDecList;
        // Expr exprCheck;

        // //checa se a função possui mesma lista de parâmetros aos parâmetros passados para ela

        // for(int i = 0; i < eList.size(); i++){
          //   // varList = (Variable) eList.get(i);
          //   exprCheck = eList.get(i);
          //   paramFunc = func.getParams().get(i);
          //   varDecList = (VarDecStat) table.returnLocal(varList.getName());

          //   if(varDecList == null){
            //     error.message("Parâmetro " + name + "não declarado");
            //   }

        ParamDec paramFunc;
        ExprAnd checkParams;

        for(int i = 0; i < eList.size(); i++){
          checkParams = (ExprAnd) eList.get(i);
          paramFunc = func.getParams().get(i);

          Symbol check, tParam;
          check = checkParams.getType().getType();
          tParam = paramFunc.getType().getType();

          if(check != tParam){
            if (!((check == Symbol.INT && tParam == Symbol.INTLITERAL)
              || (check == Symbol.INTLITERAL && tParam == Symbol.INT)
              || (check == Symbol.STRING && tParam == Symbol.STRINGLITERAL)
              || (check == Symbol.STRINGLITERAL && tParam == Symbol.STRING)
              || (check == Symbol.BOOLEAN && tParam == Symbol.BOOLLITERAL)
              || (check == Symbol.BOOLLITERAL && tParam == Symbol.BOOLEAN))) {

            error.message("Tipo dos parâmetros não correspondem aos declarados no header");
            }
          }
        }

      }
      tipo = func.getTipo();
    }

    if(tipo != null){
      if(eList!= null)
       return new FuncCall(name, eList, tipo);

      else
        return new FuncCall(name, tipo);
    }

    else{
      if(eList != null)
        return new FuncCall(name, eList);

      else
        return new FuncCall(name);
    }
  }

  private char token;
  private int tokenPos;
  private char[] input;
  // private Hashtable<Character, VarDecStat> symbolTable;
}
