import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

class CalculatorTest {

    Calculator calculator;
    Validation validation;
    String newRPN;
    double newAnswer;

    @org.junit.jupiter.api.BeforeEach
    void setUp() throws Exception {
        String expression = "(((5-1)*2)/4)*(-1)";

        validation = new Validation(expression);

        String afterValid = validation.formatting();

        calculator = new Calculator(afterValid);

        newRPN = calculator.expressionToRPN();

        newAnswer = calculator.rpnToAnswer(newRPN);
    }

    @org.junit.jupiter.api.Test
    void expressionToRPN() {
        String correctRPN = "5 1- 2* 4/ 0 1-*";
        assertThat(newRPN, notNullValue());
        assertThat(correctRPN, is(newRPN));
    }

    @org.junit.jupiter.api.Test
    void rpnToAnswer() {
        double correctAnswer = -2.0;
        assertThat(newAnswer, notNullValue());
        assertThat(correctAnswer, is(newAnswer));
    }
}