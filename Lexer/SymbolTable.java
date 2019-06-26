//     Nome                    RA
    // Bruno Asti Baradel      726499
    // Pablo Laranjo           726577
    // Pedro Coelho            743585
    // Vinícius Crepschi       743601

package Lexer;

import java.util.Hashtable;
import AST.VarDecStat;
import AST.Func;

public class SymbolTable {
  private Hashtable<String, Object> localVariableTable;
  // private Hashtable<String, Object> globalVariableTable;
  private Hashtable<String, Object> functionTable;

  public SymbolTable() {
    this.localVariableTable = new Hashtable<String, Object>();
    // this.globalVariableTable = new Hashtable<String, Object>();
    this.functionTable = new Hashtable<String, Object>();
  }

  // Limpa tabela de variáveis locais
  public void resetLocal() {
    this.localVariableTable.clear();
  }

  public Object returnLocal(String chave) {
    return this.localVariableTable.get(chave);
  }
  //
  // public Object returnGlobal(String chave) {
  //   return this.globalVariableTable.get(chave);
  // }

  public Object returnFunction(String chave) {
    return this.functionTable.get(chave);
  }

  public void putLocal(String chave, Object valor) {
    this.localVariableTable.put(chave, valor);
  }

  // public void putGlobal(String chave, Object valor) {
  //   this.globalVariableTable.put(chave, valor);
  // }

  public void putFunction(String chave, Object valor) {
    this.functionTable.put(chave, valor);
  }
}
