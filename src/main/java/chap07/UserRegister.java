package chap07;

public class UserRegister {

    private WeakPasswordChecker passwordChecker;
    private UserRepository UserRepository;

    public UserRegister(WeakPasswordChecker passwordChecker, UserRepository UserRepository) {
        this.passwordChecker = passwordChecker;
    }

    public void register(String id, String pw, String email) {
        if (passwordChecker.checkPasswordWeak(pw)) {
            throw new WeakPasswordException();
        }
    }
}
