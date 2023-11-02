public class VMTranslator {
    public static void main(String[] args) {
        LineNumber lineNumber = new LineNumber();
        InputFile inputFile = new InputFile();
        OutputFile outputFile = new OutputFile();
        Parser parser = new Parser();
        CodeWriter codewriter = new CodeWriter();
        if(args.length == 2) {
            inputFile.setInputFile(args[0]);
            outputFile.setOutputFile(args[1]);
        }
        else {
            System.out.println("Usage: java VMTranslator input-file.vm output-file.asm");
            System.exit(1);
        }

        
    }
}