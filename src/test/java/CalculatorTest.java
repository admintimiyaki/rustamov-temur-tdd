import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
public class CalculatorTest {
    @Test
    void testAdd() {
        Calculator calculator = new Calculator();
        assertEquals(9, calculator.add(2, 7)); // expected to pass
    }
    @ParameterizedTest
    @CsvSource({
            "1, 2, 3"
    })
    void testAddWithAnotherInput(int a, int b, int expected) {
        Calculator calculator = new Calculator();
        assertEquals(expected, calculator.add(a, b)); // expected to pass
    }
}
