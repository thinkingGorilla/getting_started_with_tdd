package chap07;

// Fake: 호출된 내역을 기록한다. 기록한 내용은 테스트 결과를 검증할 때 사용한다.
public class SpyEmailNotifier implements EmailNotifier {

    private boolean called = false;
    private String email;

    public boolean isCalled() {
        return called;
    }

    public String getEmail() {
        return email;
    }

    @Override
    public void sendRegisterEmail(String email) {
        this.called = true;
        this.email = email;
    }
}
