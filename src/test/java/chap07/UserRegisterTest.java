package chap07;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class UserRegisterTest {

    private UserRegister userRegister;
    private StubWeakPasswordChecker stubPasswordChecker = new StubWeakPasswordChecker();
    private MemoryUserRepository fakeUserRepository = new MemoryUserRepository();
    private SpyEmailNotifier spyEmailNotifier = new SpyEmailNotifier();


    @BeforeEach
    void setUp() {
        userRegister = new UserRegister(stubPasswordChecker, fakeUserRepository, spyEmailNotifier);
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

    @DisplayName("같은 ID가 없으면 가입 성공함")
    @Test
    void noDupId_RegisterSuccess() {
        userRegister.register("id", "pw", "email@email.com");

        User user = fakeUserRepository.findById("id");
        assertEquals("id", user.getId());
        assertEquals("email@email.com", user.getEmail());
    }

    @DisplayName("가입하면 메일을 전송함")
    @Test
    void register_SendEmail() {
        userRegister.register("id", "pw", "email@email.com");

        assertTrue(spyEmailNotifier.isCalled());
        assertEquals("email@email.com", spyEmailNotifier.getEmail());
    }
}
