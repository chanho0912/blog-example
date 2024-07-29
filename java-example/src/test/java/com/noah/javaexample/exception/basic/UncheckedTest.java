package com.noah.javaexample.exception.basic;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class UncheckedTest {

    private static final Logger log = LoggerFactory.getLogger(UncheckedTest.class);

    @Test
    void unchecked_catch() {
        Service service = new Service();
        service.callCatch();
    }

    @Test
    void unchecked_throw() {
        Service service = new Service();
        Assertions.assertThrows(MyUncheckedException.class, service::callThrow);
    }

    /**
     * RuntimeException을 상속 받은 예외는 Unchecked 예외가 된다.
     */
    static class MyUncheckedException extends RuntimeException {
        public MyUncheckedException(String message) {
            super(message);
        }
    }

    /**
     * UnChecked 예외는 예외를 잡거나, 던지지 않아도 된다.
     * 예외를 잡지 않으면 자동으로 밖으로 던진다.
     */
    static class Service {
        Repository repository = new Repository();

        /**
         * 필요한 경우 예외를 잡아서 처리하면 됨.
         */
        public void callCatch() {
            try {
                repository.call();
            } catch (MyUncheckedException e) {
                log.info("예외 처리, message = {}", e.getMessage(), e);
            }
        }

        public void callThrow() {
            repository.call();
        }

    }

    static class Repository {
        public void call() {
            throw new MyUncheckedException("ex");
        }
    }

}
