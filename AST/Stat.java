//     Nome                    RA
    // Bruno Asti Baradel      726499
    // Pablo Laranjo           726577
    // Pedro Coelho            743585
    // Vinícius Crepschi       743601

package AST;

//Stat ::= AssignExprStat | ReturnStat | VarDecStat | IfStat | WhileStat

abstract public class Stat {
  abstract public void genC(PW pw);
}
