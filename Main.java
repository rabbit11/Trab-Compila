    //     Nome                    RA
    // Bruno Asti Baradel      726499
    // Pablo Laranjo           726577
    // Pedro Coelho            743585
    // Vinícius Crepschi       743601

public class Main {
    public static void main( String []args ) {
        char []input = "------".toCharArray();
        
        Compiler compiler = new Compiler();
        //compiler.compile(input);
        System.out.println(compiler.compile(input));        
    }
}
        