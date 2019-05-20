    //     Nome                    RA
    // Bruno Asti Baradel      726499
    // Pablo Laranjo           726577
    // Pedro Coelho            743585
    // Vinícius Crepschi       743601

//    comp2

/*
Expr ::= '(' oper  Expr Expr ')' | number
oper ::= '+' | '-'
number ::= '0' | ... | '9'

	 Example of program:
    
         (+ (- 5 4) 2)
*/

/*
 - um metodo para cada nao-terminal -> analisador sintatico (parser)
 - error -- aponta o erro (quando ocorrer)
 - nextToken (analisador lexico) -- posicao do proximo token valido 
 */

public class Compiler {

    public int compile( char []p_input ) {
		int resultado;        
		input = p_input;
        tokenPos = 0;
        nextToken();
        resultado = expr();
		    
	    if ( token != '\0' )
          error();

		return resultado;
    }

    private void nextToken() {
		
		while(tokenPos < input.length && input[tokenPos] == ' '){
			tokenPos++;
		}

		if(tokenPos >= input.length)
			token = '\0';
		else {
			token = input[tokenPos];
			tokenPos++;
		}
    }
    
    private void error() {
        if ( tokenPos == 0 ) 
          tokenPos = 1; 
        else 
          if ( tokenPos >= input.length )
            tokenPos = input.length;
        
        String strInput = new String( input, tokenPos - 1, input.length - tokenPos + 1 );
        String strError = "Error at \"" + strInput + "\"";
        System.out.println( strError );
        throw new RuntimeException(strError);
    }
    
    private char token;
    private int  tokenPos;
    private char []input;
      
}
