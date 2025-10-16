import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

public class CalculatorTest {
    @Test
    void testAdd() {
        Calculator calculator = new Calculator();
        assertEquals(9, calculator.add(2, 7));
    }

    @ParameterizedTest
    @CsvSource({
            "1, 2, 3"
    })
    void testAddWithAnotherInput(int a, int b, int expected) {
        Calculator calculator = new Calculator();
        assertEquals(expected, calculator.add(a, b));
    }

    @Test
    void testSubtract() {
        Calculator calculator = new Calculator();
        assertEquals(5, calculator.subtract(10, 5));
    }

    @ParameterizedTest
    @CsvSource({
            "8, 4, 4",
            "5, 2, 3",
            "0, 0, 0"
    })
    void testSubtractWithVariousInputs(int a, int b, int expected) {
        Calculator calculator = new Calculator();
        assertEquals(expected, calculator.subtract(a, b)); // passed test
    }
}
