package com.noah.javaexample;

/**
 * 기본적으로 언체크(런타임 예외)를 사용하자.
 * 체크 예외는 비즈니스 로직 상 의도적으로 던지는 예외에만 사용.
 * 이 경우 해당 예외를 잡아서 반드시 처리해야 하는 문제일 때만 체크 예외를 사용한다.
 * ex) 계좌 이체 실패 예외, 결제 시 포인트 부족 예외, 로그인 실패
 * 물론 이 경우에도 100% 체크 예외로 만들어야 하는 것은 아니다. 다만 계좌 이체 실패처럼 매우 심각한 문제는 개발자가 실수로 예외를 놓치면 안된다고 판단할 수 있다.
 * 이 경우 체크 예외로 만들어 두면 컴파일러를 통해 놓친 예외를 인지할 수 있다.
 */
public class JavaExampleApplication {
    public static void main(String[] args) {
        System.out.println("Hello World!");
    }
}
