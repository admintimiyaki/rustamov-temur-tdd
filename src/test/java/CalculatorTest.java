import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class CalculatorTest {
    @Test
    void testAdd() { // here it is expected to fail, I still haven't created Calculator.java as for now
        Calculator calculator = new Calculator();
        assertEquals(9, calculator.add(2, 7)); // failing test
    }
}