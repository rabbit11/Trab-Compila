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

  public Program compile(char[] p_input, PW pw, String fileName) {
    lexer = new Lexer(p_input);
    table = new SymbolTable();
    error = new CompilerError(pw, fileName);
    error.setLexer(lexer);

    //algumas inicializações
    input = p_input;
    tokenPos = 0;

    //funções pre-definidas na linguagem
    Expr expression = null;
    table.putFunction("readInt", new Func("readInt", Symbol.INTLITERAL));
    table.putFunction("readString", new Func("readString", Symbol.STRINGLITERAL));
    table.putFunction("print", new Func("print", expression));
    table.putFunction("println", new Func("println", expression));
    // symbolTable = new Hashtable();

    lexer.nextToken();

    Program e = program();
    if (lexer.token != Symbol.EOF)
      error.message("EOF expected");

    return e;
  }

  // Program ::= Func {Func}
  private Program program() {
    //System.out.println("Entrou na funcao program: " + lexer.token);
    ArrayList<Func> f = new ArrayList<Func>();

    while (lexer.token == Symbol.FUNCTION) {
      f.add(func());
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

    return program;
  }

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

  // ReturnStat ::= "return" Expr ";"
  public ReturnStat ReturnStat() {
     //System.out.println("Entrou na funcao ReturnStat " + lexer.token);

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
    // lexer.nextToken();

    Expr expr = expr();


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

    return new ReturnStat(expr);
  }

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
    default:
      // will never be executed
      // System.out.println("Statement expected");
      // System.out.println(lexer.token);
      return null;
    }
  }

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

    lexer.nextToken(); // ta certo isso?

    // System.out.println("Identifier: " + lexer.getStringValue());

    // name of the identifier
    String name = lexer.getStringValue();

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
    if(table.returnLocal(name) != null) {
      error.message("Variável já declarada: " + name);
    }

    VarDecStat v = new VarDecStat(name, typeVar);
    table.putLocal(name, v);

    return v;
  }

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
    ParamDec param = new ParamDec(id, typeVar);

    return param;
  }

  // ExprAnd::= ExprRel {”and” ExprRel}
  private ExprAnd exprAnd() {
     //System.out.println("Entrou na funcao exprAnd " + lexer.token);
    ExprRel esq, dir;
    ArrayList<Expr> expr = new ArrayList<Expr>();
    Type tipo;

    esq = exprRel();
    expr.add(esq);
    
    tipo = esq.getType();

    while (lexer.token == Symbol.AND) {
      lexer.nextToken();
      dir = exprRel();
      expr.add(dir);
    }

    return new ExprAnd(expr, Symbol.AND, tipo);
  }

   //  ::= LiteralInt | LiteralBoolean | LiteralString
   public Expr exprLiteral() {
     //System.out.println("Entrou na funcao exprLiteral " + lexer.token);
       Symbol op = lexer.token;

       switch(op){
       case INTLITERAL:
        //  System.out.println("linha " + lexer.getCurrentLine());
         lexer.nextToken();
         break;

        case TRUE:
          lexer.nextToken();
          break;

        case FALSE:
          lexer.nextToken();
          break;

        case STRINGLITERAL:
         lexer.nextToken();
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

      return new ExprLiteral(op);
   }

  // Expr ::= ExprAnd {”or” ExprAnd}
  public Expr expr() {
    ExprAnd esq, dir;
    Symbol op;
    ArrayList<Expr> expr = new ArrayList<Expr>();
    Type tipo;

    esq = (ExprAnd) exprAnd();
    tipo = esq.getType();

    expr.add(esq);

    while ((op = lexer.token) == Symbol.OR) {
      lexer.nextToken();
      dir = (ExprAnd) exprAnd();
      expr.add(dir);
    }
    
    return new ExprAnd(expr, Symbol.OR, tipo);
  }

  //ExprMult ::= ExprUnary {(” ∗ ” | ”/”)ExprUnary}
  private Expr exprMult() {
     //System.out.println("Entrou na funcao exprMult " + lexer.token);
    Symbol op;
    Expr esq, dir;
    esq = exprUnary();

    while ((op = lexer.token) == Symbol.MULT || op == Symbol.DIV) {
      lexer.nextToken();
      dir = exprUnary();
      esq = new ExprMult(esq, op, dir);
    }
    return esq;
  }

  // ExprAdd ::= ExprMult {(” + ” | ” − ”)ExprMult}
  private Expr exprAdd() {
    //System.out.println("Entrou na funcao exprAdd " + lexer.token);
    Symbol op;
    Expr esq, dir;
    esq = exprMult();

    while ((op = lexer.token) == Symbol.PLUS || op == Symbol.MINUS) {
      lexer.nextToken();
      dir = exprMult();
      esq = new ExprAdd(esq, op, dir);
    }
    return esq;
  }

  // AssignExprStat ::= Expr [ "=" Expr ] ";"
  public Stat assignExprStat() {
     //System.out.println("Entrou na funcao assignExprStat " + lexer.token);
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
    else if (lexer.token == Symbol.SEMICOLON) {
      lexer.nextToken();
    }
    else {
      if (lexer.token == Symbol.IDLITERAL || lexer.token == Symbol.STRINGLITERAL) {
        error.message("Incorrect symbol at: " + lexer.getStringValue());
      } else if (lexer.token == Symbol.INTLITERAL) {
        error.message("Incorrect symbol at: " + lexer.getIntValue());
      } else if (lexer.token == Symbol.BOOLLITERAL) {
        error.message("Incorrect symbol at: " + lexer.getBoolValue());
      } else {
        error.message("Incorrect symbol at: " + lexer.token);
      }
    }

    return new AssignExprStat(esq, dir);
  }

  private ExprRel exprRel() {
     //System.out.println("Entrou na funcao exprRel " + lexer.token);
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
     //System.out.println("Entrou na funcao exprUnary " + lexer.token);
    // ExprUnary ::= [ ( "+" | "-" ) ] ExprPrimary
    Symbol op = lexer.token;
    if (op == Symbol.PLUS || op == Symbol.MINUS)
      lexer.nextToken();

    Expr e = exprPrimary();

    return new ExprUnary(e, op);
  }

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
      } else {
        Variable variable = new Variable(id);
        return variable;
      }
    } else {
      // lexer.nextToken();
      return exprLiteral();
    }
  }

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
      } else{
        lexer.nextToken();
      }
    }

    Type t = null;
    if (lexer.token == Symbol.ARROW) {
      lexer.nextToken();
      t = type();
    }


    statList();

    if(table.returnFunction(name) != null){
      error.message("Função " + name + " já declarada");
    }

    if(t == null) {
      if(p == null){
        return new Func(name);
      }
      else{
        return new Func(name, p);
      }
    }
    else if(p == null) {
      return new Func(name, t.getType());
    }
    else {
      return new Func(t.getType(), name, p);
    }
  }

  // FuncCall ::= Id "(" [ Expr {”, ”Expr} ] ")"
  private Expr funcCall(String name) {
    
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

      //checa se funcdec e funccall tem mesmo num de parametros
      if(func.getParams().size() != eList.size()){
        error.message("Chamada da função "+ name + " com número diferente de parâmetros de sua declaração");
      }

      Variable varList;
      ParamDec paramFunc;
      VarDecStat varDecList;

      //checa se a função possui mesma lista de parâmetros aos parâmetros passados para ela
      for(int i = 0; i < eList.size(); i++){
        varList = (Variable) eList.get(i);
        paramFunc = func.getParams().get(i);
        varDecList = (VarDecStat) table.returnLocal(varList.getName());

        if(varDecList.getTipo() != paramFunc.getTipo()){
          error.message("Tipo de parâmetro incompatível com a declaração da função" + name);
        }
      }
    }

    return new FuncCall(name, eList);
  }

  private char token;
  private int tokenPos;
  private char[] input;
  // private Hashtable<Character, VarDecStat> symbolTable;
}
