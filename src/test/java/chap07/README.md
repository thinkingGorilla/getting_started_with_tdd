# 대역

- 외부 상황을 대역으로 흉내내어 결과를 검증한다. 
- 대역 (Test doubles)
  - Dummy: 타입만 올바른 비기능 객체. 스텁처럼 타입이 올바르지만, 스텁은 미리 설정한 값을 반한활 수 있는 객체라면 더미는 아무런 동작을 하지 않는다.
  - Stub: 구현을 단순한 것으로 대체한다. 테스트에 맞게 단순히 원하는 동작을 수행한다.
  - Fake: 제품에는 적합하지 않지만, 실제 동작하는 구현을 제공한다.
  - Spy: 호출된 내역을 기록한다. 기록한 내용은 테스트 결과를 검증할 때 사용한다.
  - Mock: 기대한 대로 상호작용하는지 행위를 검증한다. <mark>**모의 객체는 스텁이자 스파이도 된다**</mark>.