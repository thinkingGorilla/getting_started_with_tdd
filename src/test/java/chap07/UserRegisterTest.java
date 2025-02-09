package chap07;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.BDDMockito;
import org.mockito.Mockito;

import static org.junit.jupiter.api.Assertions.*;

public class UserRegisterTest {

    private UserRegister userRegister;
    private StubWeakPasswordChecker mockPasswordChecker = Mockito.mock(StubWeakPasswordChecker.class);
    private MemoryUserRepository fakeUserRepository = new MemoryUserRepository();
    private SpyEmailNotifier mockEmailNotifier = Mockito.mock(SpyEmailNotifier.class);


    @BeforeEach
    void setUp() {
        userRegister = new UserRegister(mockPasswordChecker, fakeUserRepository, mockEmailNotifier);
    }

    @DisplayName("약한 암호면 가입 실패")
    @Test
    void weakPassword() {
        BDDMockito
            .given(mockPasswordChecker.checkPasswordWeak("pw"))
            .willReturn(true);

        mockPasswordChecker.setWeak(true);

        assertThrows(
            WeakPasswordException.class,
            () -> {
                userRegister.register("id", "pw", "email@email.com");
            }
        );
    }

    @DisplayName("회원 가입 시 암호 검사 수행함")
    @Test
    void checkPassword() {
        userRegister.register("id", "pw", "email@email.com");

        BDDMockito
            .then(mockPasswordChecker)
            .should()
            .checkPasswordWeak(BDDMockito.anyString());
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
    void whenRegisterThenSendEmail() {
        userRegister.register("id", "pw", "email@email.com");

        ArgumentCaptor<String> captor = ArgumentCaptor.forClass(String.class);
        BDDMockito
            .then(mockEmailNotifier)
            .should()
            .sendRegisterEmail(captor.capture());

        String realEmail = captor.getValue();
        assertEquals("email@email.com", realEmail);
    }
}
