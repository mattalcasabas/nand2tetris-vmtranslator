import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

public class CodeWriter {
    public int symbolNumber = 0;

    public static void writeOutputLine(String line, String output) {
        BufferedWriter writer;

        try {
            writer = new BufferedWriter(new FileWriter(output, true));
            if (line != null) {
                writer.write(line);
                writer.newLine();
            }
            writer.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void writeArithmetic(String command, String output) {
        switch (command) {
            case "add":
                writeOutputLine("@SP // add", output);
                writeOutputLine("AM=M+1", output);
                writeOutputLine("D=M", output);
                writeOutputLine("A=A-1", output);
                writeOutputLine("M=D+M", output);
                break;
            case "sub":
                writeOutputLine("@SP // sub", output);
                writeOutputLine("AM=M-1", output);
                writeOutputLine("D=M", output);
                writeOutputLine("A=A-1", output);
                writeOutputLine("M=M-D", output);
                break;
            case "neg":
                writeOutputLine("@SP // neg", output);
                writeOutputLine("A=M", output);
                writeOutputLine("A=A-1", output);
                writeOutputLine("M=-M", output);
                break;
            case "eq":
                String trueLabel = "EQ_TRUE_" + Integer.toString(symbolNumber);
                String falseLabel = "EQ_FALSE_" + Integer.toString(symbolNumber);
                writeOutputLine("@SP // eq", output);
                writeOutputLine("AM=M-1", output);
                writeOutputLine("D=M", output);
                writeOutputLine("@SP", output);
                writeOutputLine("AM=M-1", output);
                writeOutputLine("D=M-D", output);
                writeOutputLine("@" + trueLabel, output);
                writeOutputLine("D;JEQ", output);
                writeOutputLine("D=0", output);
                writeOutputLine("@" + falseLabel, output);
                writeOutputLine("0;JMP", output);
                writeOutputLine("(" + trueLabel + ")", output);
                writeOutputLine("D=-1", output);
                writeOutputLine("(" + falseLabel + ")", output);
                writeOutputLine("@SP", output);
                writeOutputLine("A=M", output);
                writeOutputLine("M=D", output);
                writeOutputLine("@SP", output);
                writeOutputLine("M=M+1", output);
                symbolNumber++;
                break;
            case "gt":
                String trueLabel = "GT_TRUE_" + Integer.toString(symbolNumber);
                String falseLabel = "GT_FALSE_" + Integer.toString(symbolNumber);
                writeOutputLine("@SP // gt", output);
                writeOutputLine("AM=M-1", output);
                writeOutputLine("D=M", output);
                writeOutputLine("@SP", output);
                writeOutputLine("AM=M-1", output);
                writeOutputLine("D=M-D", output);
                writeOutputLine("@" + trueLabel, output);
                writeOutputLine("D;JGT", output);
                writeOutputLine("D=0", output);
                writeOutputLine("@" + falseLabel, output);
                writeOutputLine("0;JMP", output);
                writeOutputLine("(" + trueLabel + ")", output);
                writeOutputLine("D=-1", output);
                writeOutputLine("(" + falseLabel + ")", output);
                writeOutputLine("@SP", output);
                writeOutputLine("A=M", output);
                writeOutputLine("M=D", output);
                writeOutputLine("@SP", output);
                writeOutputLine("M=M+1", output);
                symbolNumber++;
                break;
            case "lt":
                String trueLabel = "LT_TRUE_" + Integer.toString(symbolNumber);
                String falseLabel = "LT_FALSE_" + Integer.toString(symbolNumber);
                writeOutputLine("@SP // lt", output);
                writeOutputLine("AM=M-1", output);
                writeOutputLine("D=M", output);
                writeOutputLine("@SP", output);
                writeOutputLine("AM=M-1", output);
                writeOutputLine("D=M-D", output);
                writeOutputLine("@" + trueLabel, output);
                writeOutputLine("D;JLT", output);
                writeOutputLine("D=0", output);
                writeOutputLine("@" + falseLabel, output);
                writeOutputLine("0;JMP", output);
                writeOutputLine("(" + trueLabel + ")", output);
                writeOutputLine("D=-1", output);
                writeOutputLine("(" + falseLabel + ")", output);
                writeOutputLine("@SP", output);
                writeOutputLine("A=M", output);
                writeOutputLine("M=D", output);
                writeOutputLine("@SP", output);
                writeOutputLine("M=M+1", output);
                symbolNumber++;
                break;
            case "and":
                writeOutputLine("@SP // and", output);
                writeOutputLine("AM=M-1", output);
                writeOutputLine("D=M", output);
                writeOutputLine("A=A-1", output);
                writeOutputLine("M=D&M", output);
                break;
            case "or":
                writeOutputLine("@SP // or", output);
                writeOutputLine("AM=M-1", output);
                writeOutputLine("D=M", output);
                writeOutputLine("A=A-1", output);
                writeOutputLine("M=D|M", output);
                break;
            case "not":
                writeOutputLine("@SP // not", output);
                writeOutputLine("A=M", output);
                writeOutputLine("A=A-1", output);
                writeOutputLine("M=D&M", output);
                break;
            case "end":
                writeOutputLine("(END) // end of program loop", output);
                writeOutputLine("@END", output);
                writeOutputLine("0;JMP", output);
                break;
            default:
                System.out.println("writeArithmetic(): invalid case");
                System.exit(1);
        }
    }

    public String segmentToPredefinedSymbol(String segment) {
        switch (segment) {
            case "local":
                return "LCL";
            case "argument":
                return "ARG";
            case "this":
                return "THIS";
            case "that":
                return "THAT";
            default:
                return segment;
        }
    }

    public void writePushPop(String command, String segment, int index, String output) {
        String indexString = null;
        if (index != -1) {
            indexString = Integer.toString(index);
        }
        switch (command) {
            case "push":
                switch (segment) {
                    case "constant":
                        String atValue = "@" + indexString;
                        writeOutputLine(atValue, output);
                        writeOutputLine("D=A", output);
                        writeOutputLine("@SP", output);
                        writeOutputLine("A=M", output);
                        writeOutputLine("M=D", output);
                        writeOutputLine("@SP", output);
                        writeOutputLine("M=M+1", output);
                        break;
                    case "static":
                    case "argument":
                    case "local":
                    case "this":
                    case "that":
                    case "temp":
                    case "pointer":
                        writeOutputLine("@" + segmentToPredefinedSymbol(segment), output);
                        writeOutputLine("D=M", output);
                        writeOutputLine("@" + indexString, output);
                        writeOutputLine("A=D+A", output);
                        writeOutputLine("D=M", output);
                        writeOutputLine("@SP", output);
                        writeOutputLine("A=M", output);
                        writeOutputLine("M=D", output);
                        writeOutputLine("@SP", output);
                        writeOutputLine("M=M+1", output);
                        break;
                    default:
                        System.out.println("writePushPop(): unknown segment type");
                        System.exit(1);
                }
            case "pop":
                switch (segment) {
                    case "constant":
                        System.out.println("writePushPop(): cannot pop to constant");
                        System.exit(1);
                    case "static":
                    case "argument":
                    case "local":
                    case "this":
                    case "that":
                    case "temp":
                    case "pointer":
                        writeOutputLine("@" + segment, output);
                        writeOutputLine("D=M", output);
                        writeOutputLine("@" + indexString, output);
                        writeOutputLine("D=D+A", output);
                        writeOutputLine("@R13", output);
                        writeOutputLine("M=D", output);
                        writeOutputLine("@SP", output);
                        writeOutputLine("AM=M-1", output);
                        writeOutputLine("D=M", output);
                        writeOutputLine("@R13", output);
                        writeOutputLine("A=M", output);
                        writeOutputLine("M=D", output);
                        break;
                }
        }
    }

    public void writeLabel(String label)
}
