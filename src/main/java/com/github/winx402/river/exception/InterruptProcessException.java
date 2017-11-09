package com.github.winx402.river.exception;

/**
 * @author wangwenxiang
 * @create 2017-05-14.
 */
public class InterruptProcessException extends RuntimeException {
    public InterruptProcessException() {
        super();
    }

    public InterruptProcessException(String message) {
        super(message);
    }

    public InterruptProcessException(String message, Throwable cause) {
        super(message, cause);
    }
}
