import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class Parser {
    public static String readLineFromFile(String input, int lineNumber) throws IOException {
        BufferedReader reader = new BufferedReader(new FileReader(input));
        String line = null;
        int currentLine = 0;

        while ((line = reader.readLine()) != null) {
            currentLine++;

            if (currentLine == lineNumber) {
                reader.close();
                return line;
            }
        }

        reader.close();
        return null;
    }
    public boolean hasMoreCommands() {
        LineNumber lineNumber = new LineNumber();
        InputFile inputFile = new InputFile();
        try {
            if (readLineFromFile(inputFile.getInputFile(), lineNumber.getLineNumber() + 1) == null) {
                return false;
            }
        } catch (IOException e) {
            return false;
        }
        return true;
    }

    public String trimComment(String input) {
        int commentIndex = input.indexOf("//");

        if (commentIndex != -1) {
            return input.substring(0, commentIndex);
        }
        else {
            return input;
        }
    }

    public String trimWhitespace(String input) {
        return input.trim();
    }

    public String commandType(String input) {
        switch (input) {
            case "add":
            case "sub":
            case "neg":
            case "eq":
            case "gt":
            case "lt":
            case "and":
            case "or":
            case "not":
                return "C_ARITHMETIC";
            case "push":
                return "C_PUSH";
            case "pop":
                return "C_POP";
            case "label":
                return "C_LABEL";
            case "goto":
                return "C_GOTO";
            case "if-goto":
                return "C_IF";
            case "function":
                return "C_FUNCTION";
            case "call":
                return "C_CALL";
            case "return":
                return "C_RETURN";
            default:
                return null;
        }
    }

    public String[] sortCommand(String input) {
        String[] output = new String[3];
        input = trimComment(trimWhitespace(input));
        output = input.split("\\s+");
        return output;
    }

    public String arg1(String[] input, String commandType) {
        if (commandType == "C_ARITHMETIC") {
            return input[1];
        }
        else if (commandType != "C_RETURN") {
            return input[2];
        }
        else {
            return null;
        }
    }

    public int arg2(String[] input, String commandType) {
        switch(commandType) {
            case "C_PUSH":
            case "C_POP":
            case "C_FUNCTION":
            case "C_CALL":
                int arg2 = Integer.parseInt(input[3]);
                return arg2;
            default:
                return -1;
        }
    }
}
