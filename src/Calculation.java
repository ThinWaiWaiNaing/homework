import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Calculation {
    public static void main(String[] args) {
        try {         
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter the operation:");
        BigDecimal calResult = evaluate(scanner.nextLine());
        System.out.println("Calculation Result:" + calResult.setScale(1, RoundingMode.HALF_UP));
        scanner.close();        
        } catch (RuntimeException e) {
          System.out.println("計算式が正しくないです。");
        }
    }

    private static BigDecimal evaluate(String expresion) {
        BigDecimal result = new BigDecimal(0);
        String operation = "";
        List<Character> openBrackets = new ArrayList<Character>();
        List<Character> closeBrackets = new ArrayList<Character>();
        StringBuilder innerInput =new StringBuilder();
        String firstVal = "";
        String secondVal = "";

        for (int i = 0; i < expresion.length(); i++) {
            char inputChar = expresion.charAt(i);
            if(openBrackets.isEmpty()) {
                if (Character.isDigit(inputChar) || inputChar == '.') {
                    if (operation == "" && result.compareTo(BigDecimal.ZERO) == 0) {
                        firstVal += inputChar;
                        continue;
                    }
                    else if (operation != "") {
                        secondVal += inputChar;
                        if(i == expresion.length()-1) {
                            if(firstVal.equals("")) {
                                firstVal = result.toString();
                            }
                            result = calculateWithOperation(operation,new BigDecimal(secondVal), new BigDecimal(firstVal));
                        }
                        continue;
                    }
                }
                if (inputChar == '+' || inputChar == '-' || inputChar == '*' || inputChar == '/') {
                    operation = Character.toString(inputChar);
                    continue;
                }
            }
            if (inputChar == '(') {
                if(!openBrackets.isEmpty()) {
                    innerInput.append(inputChar);
                }
                openBrackets.add(inputChar);
                continue;
            }
            if(inputChar ==')') {
                closeBrackets.add(inputChar);
                if(openBrackets.size() == closeBrackets.size()) {
                    openBrackets.clear();
                    closeBrackets.clear();
                    result =  evaluate(innerInput.toString());
                    if (operation == "" && firstVal == "") {
                        firstVal = result.toString();
                    }
                    if(firstVal != "" && operation != "") {
                        result = calculateWithOperation(operation,result, new BigDecimal(firstVal));
                    }
                    innerInput.setLength(0);
                }
                if(openBrackets.size() > closeBrackets.size()) {
                    innerInput.append(inputChar);
                    continue;
                }
            }
            else{
                innerInput.append(inputChar);
            }
        }
        return result;
    }

    private static BigDecimal calculateWithOperation(String operation, BigDecimal inputChar, BigDecimal output) {
        switch (operation) {
        case "+":
            output = output.add(inputChar);
            break;

        case "-":
            output = output.subtract(inputChar);
            break;

        case "*":
            output = output.multiply(inputChar);
            break;

        case "/":
            output = output.divide(inputChar);
            break;

        default:
            break;
        }
        return output;
    }
}
