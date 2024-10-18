package chap02;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * - 검사할 규칙은 다음 세 가지이다.
 * 1. 길이가 8글자 이상
 * 2. 0부터 9 사이의 숫자를 포함
 * 3. 대문자 포함
 * - 세 규칙을 모두 충족하면 암호는 강함이다.
 * - 2개의 규칙을 모두 충족하면 암호는 보통이다.
 * - 1개 이하의 규칙을 충족하면 암호는 약함이다.
 */
public class PasswordStrengthMeterTest {

    private PasswordStrengthMeter meter = new PasswordStrengthMeter();

    private void assertStrength(String password, PasswordStrength expSt) {
        PasswordStrength result = meter.meter(password);
        assertEquals(expSt, result);
    }

    // 첫 번째 테스트는 모든 규칙이 충족하는 경우 또는 모든 조건을 충족하지 않는 경우에 대해 작성한다.
    @Test
    void meetsAllCriteria_Then_Strong() {
        // 모든 규칙을 지키는 테스트를 작성하자.
        assertStrength("ab12!@AB", PasswordStrength.STRONG);
        assertStrength("abc1!Add", PasswordStrength.STRONG);
    }

    @Test
    void meetsOtherCriteria_except_for_Length_Then_Normal() {
        // 암호 강도가 보통인 경우에 대한 테스트를 작성하자.
        assertStrength("ab12!@A", PasswordStrength.NORMAL);
        assertStrength("Ab12!c", PasswordStrength.NORMAL);
    }

    @Test
    void meetsOtherCriteria_except_for_number_Then_Normal() {
        // 암호 강도가 보통인 경우에 대한 테스트를 작성하자.
        // 단, 숫자를 포함하지 않고 나머지 조건을 충족하는 경우에 대한 테스트를 작성하자.
        assertStrength("ab!@ABqwer", PasswordStrength.NORMAL);
    }

    @Test
    void nullInput_Then_Invalid() {
        assertStrength(null, PasswordStrength.INVALID);
    }

    @Test
    void emptyInput_Then_Invalid() {
        assertStrength("", PasswordStrength.INVALID);
    }

    @Test
    void meetsOtherCriteria_except_for_Uppercase_Then_Normal() {
        // 암호 강도가 보통인 경우에 대한 테스트를 작성하자.
        // 단, 대문자를 포함하지 않고 나머지 조건을 충족하는 경우에 대한 테스트를 작성하자.
        assertStrength("ab12!@df", PasswordStrength.NORMAL);
    }
}
