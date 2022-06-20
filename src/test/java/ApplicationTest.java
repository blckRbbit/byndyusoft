import exception.BSEmptyExpressionException;
import exception.BSUnexpectedCharacterException;
import exception.BSUnexpectedTokenException;
import org.junit.jupiter.api.Test;
import parser.BSCalculatorParser;
import service.BSCalculatorService;

import static org.junit.jupiter.api.Assertions.*;

public class ApplicationTest {

    @Test
    void isNotQuit() {
        assertFalse(BSCalculatorService.isNotQuitForTest("q"));
        assertFalse(BSCalculatorService.isNotQuitForTest("Q"));
    }

    @Test
    void greet() {
        BSCalculatorService.greetForTest();
    }

    @Test
    void toInvite() {
        BSCalculatorService.toInviteForTest();
    }

    @Test
    void sayGoodbye() {
        BSCalculatorService.sayGoodbyeForTest();
    }

    @Test
    void getExpression() {
        String expression = "1 + 1";
        String result = BSCalculatorService.getExpressionForTest(expression);
        assertEquals(result, expression);
    }

    @Test
    void calculate() {
        String expression1 = "2 + 2 * 2";
        String expression2 = "122 - 34 - 3*  (55   + 5    * (3 - 2)) * 2";
        assertEquals(BSCalculatorParser.calculate(expression1), 6);
        assertEquals(BSCalculatorParser.calculate(expression2), -272);
    }


    @Test
    void calculateThrowBSUnexpectedCharacterException() {
        String expression = "2 + 2 * 2 = abc";
        assertThrows(BSUnexpectedCharacterException.class, () -> BSCalculatorParser.calculate(expression));
    }

    @Test
    void calculateBSEmptyExpressionException() {
        String expression = "";
        String expression2 = "    ";
        assertThrows(BSEmptyExpressionException.class, () -> BSCalculatorParser.calculate(expression));
        assertThrows(BSEmptyExpressionException.class, () -> BSCalculatorParser.calculate(expression2));
    }

    @Test
    void calculateBSUnexpectedTokenException() {
        String expression2 = "122 - 34 - 3*  (55   + 5    * (3 - 2) * 2";
        assertThrows(BSUnexpectedTokenException.class, () -> BSCalculatorParser.calculate(expression2));
    }

}
