import java.util.Stack;

/**
 *
 */
public class Calculator {

    private final String sourceString; //input
    private StringBuilder workingString; //output

    public Calculator(String sourceString) {
        this.sourceString = sourceString;
    }


    /**
     * @return - It's an expression in the form of Reverse Polish Notation.
     */
    public String expressionToRPN() {
        workingString = new StringBuilder();

        Stack<Character> operatorsStack = new Stack<>();

        int priority;
        char token;

        // с помощью этого цикла проходимся по всему выражению
        for (int i = 0; i < sourceString.length(); i++) {
            token = sourceString.charAt(i);

            priority = getPriority(token);


            // -1 - it's a numeral
            // если встречаем число, то добавляем
            // его результирующую строку
            if (priority == -1) {
                workingString.append(token);
            }

            // >1 - these are operations on numbers
            if (priority > 1) {

                // добавляем пробелы, чтобы числа "не слипались"
                // в одно большое число, т е при 1+2*3, может
                // получится 123*+, но должно быть 1 2 3*+
                workingString.append(' ');

                while (!operatorsStack.empty()) {

                    // сравниваем арифметические операции по приоритету
                    if (getPriority(operatorsStack.peek()) >= priority) {

                        // извлекаем из стека последний элемент и добавляем
                        // в результирующую строку
                        workingString.append(operatorsStack.pop());
                    } else {
                        break;
                    }
                }
                // после сравнения, кладем символ в стек
                // с арифметическими операциями
                operatorsStack.push(token);
            }

            // 1 - it's an opening bracket
            if (priority == 1) {
                operatorsStack.push(token);
            }

            // 0 - it's a closing bracket
            if (priority == 0) {

                //пока не дойдем до открывающей скобки
                while (getPriority(operatorsStack.peek()) != 1) {

                    //переносим операторы в результирующую строку
                    workingString.append(operatorsStack.pop());
                }

                // а саму открывающую скобку удаляем
                operatorsStack.pop();
            }
        }

        // на случай, если наш стек операторов не пустой,
        // переносим его содержимое в результирующую строку
        while (!operatorsStack.empty()) {
            workingString.append(operatorsStack.pop());
        }

        return workingString.toString();
    }


    /**
     * @param rpnExpression - It's an expression in the form of Reverse Polish Notation.
     * @return - It's an answer (double).
     * @throws Exception - If there's an unsuitable sign.
     *
     * In this method, we parse a string in the form of Reverse Polish Notation
     * and get an answer as a double.
     */
    public double rpnToAnswer(String rpnExpression) throws Exception {
        workingString = new StringBuilder();

        Stack<Double> resultStack = new Stack<>();

        // с помощью этого цикла проходимся по всему выражению
        for (int i = 0; i < rpnExpression.length(); i++) {

            // если встречаем пробел, то продвигаемся дальше по алгоритму
            if (rpnExpression.charAt(i) == ' ') { continue; }

            // если встречаем число, то проходимся по каждой цифре
            // и записываем результат (в виде числа) в результирующий стек
            if (getPriority(rpnExpression.charAt(i)) == -1) {

                while (rpnExpression.charAt(i) != ' ' && getPriority(rpnExpression.charAt(i)) == -1) {

                    workingString.append(rpnExpression.charAt(i++));
                }

                // в стек также будут попадать результаты после арифметических операций,
                // поэтому его тип Double. Благодаря чему, нам необходим перевести
                // полученное число в double
                double numbers = Double.parseDouble(workingString.toString());
                resultStack.push(numbers);

                workingString = new StringBuilder();
            }


            //здесь и происходят арифметические операции
            if (getPriority(rpnExpression.charAt(i)) > 1) {
                operationsOnNumbers(rpnExpression, resultStack, i);
            }
        }

        //берем и удаляем из стека результат
        return resultStack.pop();
    }


    /**
     * @param rpnExpression - It's an expression in the form of Reverse Polish Notation.
     * @param resultStack - It's a stack containing numbers from the form of Reverse Polish Notation.
     * @param token - It's a numeral.
     * @throws Exception - If there's an unsuitable sign.
     *
     * We pop last two numbers from the resultStack
     * and perform operations on them,
     * then we push the result back onto the stack.
     */
    private void operationsOnNumbers(String rpnExpression, Stack<Double> resultStack, int token) throws Exception {
        double firstTerm = resultStack.pop();
        double secondTerm = resultStack.pop();

        switch (rpnExpression.charAt(token)) {
            case '+':
                resultStack.push(firstTerm + secondTerm);
                break;
            case '-':
                resultStack.push(secondTerm - firstTerm);
                break;
            case '*':
                resultStack.push(firstTerm * secondTerm);
                break;
            case '/':
                resultStack.push(secondTerm / firstTerm);
                break;
            default:
                throw new Exception("This action" +  rpnExpression.charAt(token) + "is not supported!");
        }
    }


    /**
     * @param token - It's a symbol in a string.
     * @return - It's a priority of the symbol.
     */
    private int getPriority(char token) {
        if (token == '*' || token == '/') {
            return 3;
        } else if(token == '+' || token == '-') {
            return 2;
        } else if (token == '(') {
            return 1;
        } else if (token == ')') {
            return 0;
        } else {
            return -1; //it's a numeral.
        }
    }
}