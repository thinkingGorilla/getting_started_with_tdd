package chap07;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class UserRegisterTest {

    private UserRegister userRegister;
    private StubWeakPasswordChecker stubPasswordChecker = new StubWeakPasswordChecker();
    private final MemoryUserRepository fakeUserRepository = new MemoryUserRepository();

    @BeforeEach
    void setUp() {
        userRegister = new UserRegister(stubPasswordChecker, fakeUserRepository);
    }

    @DisplayName("약한 암호면 가입 실패")
    @Test
    void weakPassword() {
        stubPasswordChecker.setWeak(true);

        assertThrows(
            WeakPasswordException.class,
            () -> {
                userRegister.register("id", "pw", "email@email.com");
            }
        );
    }

    @DisplayName("이미 같은 ID가 존재하면 가입 실패")
    @Test
    void dupIdExists() {
        userRegister.register("id", "pw", "email@email.com");

        assertThrows(
            DupIdException.class,
            () -> {
                userRegister.register("id", "pw", "email@email.com");
            }
        );
    }
}
