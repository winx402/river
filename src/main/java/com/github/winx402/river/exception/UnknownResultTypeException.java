package com.github.winx402.river.exception;

/**
 * @author wangwenxiang
 * @create 2017-05-14.
 */
public class UnknownResultTypeException extends RuntimeException {
    public UnknownResultTypeException() {
        super();
    }

    public UnknownResultTypeException(String message) {
        super(message);
    }

    public UnknownResultTypeException(String message, Throwable cause) {
        super(message, cause);
    }
}
