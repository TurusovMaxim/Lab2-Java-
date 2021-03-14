/**
 * Разбор выражения и вычисление его значения.
 * @author TurusovMaxim
 */
public class Program {
    public static void main(String[] args) throws Exception {
        String exp = "(((5-1)*2)/4)*(-1)";
        Validation validation = new Validation(exp);
        String afterValid = validation.formatting();
        System.out.println(afterValid);

        Calculator calculator = new Calculator(afterValid);

        String rpn = calculator.expressionToRPN();

        System.out.println(rpn);

        Double answer = calculator.rpnToAnswer(rpn);
        System.out.println(answer);
    }
}
