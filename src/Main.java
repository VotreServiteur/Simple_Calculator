import java.util.Scanner;
import java.util.Stack;

public class Main {

    private static int getPrecedence(char operator) {
        return switch (operator) {
            case '^' -> 3;
            case '*', '/' -> 2;
            case '+', '-' -> 1;
            default -> -1;
        };
    }
    public static boolean isOperator(char c){
        return c == '+' || c == '-' || c == '*'|| c == '/' || c == '^' ;
    }

    public static String infixToPostfix(String expr){
        StringBuilder postfix = new StringBuilder();
        Stack<Character> stack = new Stack<>();

        for (int i = 0; i < expr.length(); i++) {
            char c = expr.charAt(i);
            if (c == ' ') continue;
            if (Character.isDigit(c) || c == '.' || (c == '-' && Character.isDigit(expr.charAt(i + 1)))){
                postfix.append(c);
            } else if (c == '(') {
                stack.push(c);
            } else if (c == ')') {
                while (!stack.isEmpty() && stack.peek() != '('){
                    postfix.append(' ').append(stack.pop());
                }
                if (!stack.isEmpty() && stack.peek() == '('){
                    stack.pop();
                }
            } else if (isOperator(c)){
                postfix.append(' ');
                while(!stack.isEmpty() && isOperator(stack.peek()) &&
                        getPrecedence(stack.peek()) >= getPrecedence(c)){
                    postfix.append(stack.pop()).append(' ');
                }
                stack.push(c);

            }
        }
        while (!stack.isEmpty()){
            postfix.append(' ').append(stack.pop());
        }

        return postfix.toString();
    }

    public static double solveExpression(String expr){
        String postfix = infixToPostfix(expr);

        Stack<Double> stack = new Stack<>();
        String[] tokens = postfix.split("\\s+");
        for(String token: tokens){
            if (token.matches("-?\\d.?\\d?")){
                stack.push(Double.parseDouble(token));
            } else if (token.length() == 1 && isOperator(token.charAt(0))) {
                double b = stack.pop();
                double a = stack.pop();
                switch(token.charAt(0)){
                    case '+' -> stack.push(a + b);
                    case '-' -> stack.push(a - b);
                    case '*' -> stack.push(a * b);
                    case '/' -> stack.push(a / b);
                    case '^' -> stack.push(Math.pow(a, b));
                }
            }
        }
        return stack.pop();
    }

    public static void main(String[] args) {
        double result;
        String expression;

        System.out.println("Write your expression:");
        var sc = new Scanner(System.in);
        expression = sc.nextLine();
        sc.close();

        System.out.print(expression + " = ");
        result = solveExpression(expression);
        System.out.print((int) result == result ? "" + (int)result : "" + result);

    }
}