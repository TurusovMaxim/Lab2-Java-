import static org.junit.jupiter.api.Assertions.*;

class CalculatorTest {

    Calculator calculator;
    Validation validation;
    String rpnExpression;
    double answer;

    @org.junit.jupiter.api.BeforeEach
    void setUp() throws Exception {
        String expression = "(((5-1)*2)/4)*(-1)";

        validation = new Validation(expression);

        String afterValid = validation.formatting();

        calculator = new Calculator(afterValid);

        rpnExpression = calculator.expressionToRPN();

        answer = calculator.rpnToAnswer(rpnExpression);
    }

    @org.junit.jupiter.api.Test
    void expressionToRPN() {
        assertEquals("5 1- 2* 4/ 0 1-*", rpnExpression);
    }

    @org.junit.jupiter.api.Test
    void rpnToAnswer() {
        assertEquals(-2.0, answer);
    }
}