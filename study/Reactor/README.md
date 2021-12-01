Reactor & Web Flux 이 것 저 것 정리 중 

- `Observer Pattern`의 연장선 (Publisher - Subscriber) 
  - Publisher가 Subscriber에게 새로운 데이터를 알리는 로직이 선언적으로 표현되어야함 
  - Publisherrk `onNext` 호출 -> Subscriber에 알림 

# Blocking은 낭비적이다 

보통 퍼포먼스 향상을 위해 할 수 있는 것이 두 가지가 있음
1. 스레드 여러 개를 병렬로 실행 
2. 현재 가진 자원에서 더 효율성을 찾기 

보통 자바 개발자들은 blocking code를 작성함 그래서 멀티 스레드를 사용한다 해도 여전히 blocking code를 쓴단말임.. 결국 이렇게 해봤자 동시성 문제가 생김

(동시성 문제 찾아보기)

더 안좋은 것은 저런 과정에서 자원들을 낭비하는데, 자세히 보면 쓰레드가 계~~~속 DB같은 IO 요청 응답에 대기해서 효율이 정말 구리다

그래서 한정된 자원에서 빠르고 효율적으로 이 문제를 해결할 수 있는 방법이 비동기적으로 논블로킹 코드를 짜는 것이다. 기존 JVM에서 비동기적으로 코드를 짜려면 두 가지 정도를 사용 가능했음..

CallBack과 Futures이다

하지만 익히 들어봤겠지만 콜백지옥이라는 말이 괜히 있는게 아니고, (아래 참고)

![callback hell](https://media.vlpt.us/images/2yeseul/post/072bbe22-41bd-4253-a8b3-9d19144d3e1d/%E1%84%89%E1%85%B3%E1%84%8F%E1%85%B3%E1%84%85%E1%85%B5%E1%86%AB%E1%84%89%E1%85%A3%E1%86%BA%202021-12-01%20%E1%84%8B%E1%85%A9%E1%84%92%E1%85%AE%209.02.11.png)

요 콜백지옥의 코드는 Reactor code로 아래처럼 리팩토링할 수 있다 

``` java 
userService.getFavorites(userId)
           .flatMap(favoriteService::getDetails)
           .switchIfEmpty(suggestionService.getSuggestions())
           .take(5)
           .publishOn(UiUtils.uiThreadScheduler())
           .subscribe(uiList::show, UiUtils::errorPopUp);
```

- `Future`와 `CompetableFuture` 역시 몇 가지 문제점이 있는데, 여러 Future들을 함께 사용하는게 쉬운 일이 아닐 뿐더러,
- `Future` 객체가 `get()` 호출하면서 다시 blocking이 될 수 있기 때문..
- 또 lazy 연산 지원 안해주고
- 다중 값들이나 에러 핸들링을 지원 잘 안해줌 .. 

# Operator
- publisher에 행동을 추가함
- subscriber가 publisher 구독 전까진 아무일도 없음

# Flux vs Mono 
Flux는 n개의 리액티브한 순서의 (?.. 흐름?) 아이템을 가질 수 있는 객체이고.. Mono는 하나밖에 못가짐 
- 둘의 차이는 비동기 처리할 때 대략적으로 원소의 개수가 몇 개 인지에 대한 정보를 제공하는 정도??
  - onNext 이후 onComplete 호출 가능 but onNext랑 onError 같이 호출하면 ㄴㄴ
  - Mono의 concatWith는 Flux를 리턴 / Mono의 then은 다른 Mono를 리턴 
  - Mono를 비동기 프로세스에 대해 값이 존재하지 않다는 표현으로 사용도 가능
