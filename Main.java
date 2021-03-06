    //     Nome                    RA
    // Bruno Asti Baradel      726499
    // Pablo Laranjo           726577
    // Pedro Coelho            743585
    // Vinícius Crepschi       743601

import AST.*;
import java.io.*;


public class Main {
  public static void main( String []args ) {
    File file;
    FileReader stream;
    int numChRead;
    Program program;

    if ( args.length != 2 ) {
      System.out.println("Usage:\n Main input output");
      System.out.println("input is the file to be compiled");
      System.out.println("output is the file where the generated code will be stored");
    }
    else {
      file = new File(args[0]);
      if ( ! file.exists() || ! file.canRead() ) {
        System.out.println("Either the file " + args[0] + " does not exist or it cannot be accessed");
          throw new RuntimeException();
      }

      try {
        stream = new FileReader(file);
      } catch ( FileNotFoundException e ) {
          System.out.println("Something wrong: file does not exist anymore");
          throw new RuntimeException();
        }

    // one more character for ’\0’ at the end that will be added by the
    // compiler

      char []input = new char[ (int ) file.length() + 1 ];
      try {
        numChRead = stream.read( input, 0, (int ) file.length() );
        } catch ( IOException e ) {
          System.out.println("Error reading file " + args[0]);
          throw new RuntimeException();
        }
      if ( numChRead != file.length() ) {
        System.out.println("Read error");
        throw new RuntimeException();
      }

      try {
        stream.close();
      } catch ( IOException e ) {
        System.out.println("Error in handling the file " + args[0]);
        throw new RuntimeException();
      }

      Compiler compiler = new Compiler();
      FileOutputStream outputStream;
      try {
        outputStream = new FileOutputStream(args[1]);
      } catch ( IOException e ) {
        System.out.println("File " + args[1] + " could not be opened for writing");
        throw new RuntimeException();
      }

      program = null;
      PrintWriter printWriter = new PrintWriter(outputStream, true);
      PW pw = new PW(printWriter);

      // the generated code goes to a file and so are the errors
      try {
        String path = args[0];
        String fileName = "";

        //obtendo o nome do arquivo de entrada, sem incluir o caminho para o mesmo
        for(int i = path.lastIndexOf('/'); i < path.length(); i++){
          fileName += path.charAt(i);
        }

      program = compiler.compile(input, pw, fileName); //RETIRADO PRINTWRITER DOS PARAMETROS DA FUNÇÃO COMPILE
      } catch ( RuntimeException e ) {
        System.out.println(e);
      }

      compiler = new Compiler();

      if(program != null && program.getError() == false){
        program.genC(pw);
      }
      else{
        System.out.println("Erro de compilação detectado, geração de código em C abortada");
      }


      //PrintWriter printWriter = new PrintWriter(outputStream);
      //PW pw = new PW(printWriter);


       // if ( program != null ) {
       //   pw = new PW(printWriter);
       //   pw.set(2); //PARAMETRO TROCADO SÓ PRA COMPILAR
       //   program.genC(pw);
       //   if ( printWriter.checkError() ) {
       //     System.out.println("There was an error in the output");
       //   }
       // }
    }
  }
}
