package chap02;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class CalculatorTest {

    // 한 번에 코드를 구현하는 것이 아니라 테스트 코드를 작성하며 점진적으로 코드를 구현한다.
    @Test
    void plus() {
        int result = Calculator.plus(1, 2);
        assertEquals(3, result);
    }
}
