package com.github.winx402.river.exception;

/**
 * @author wangwenxiang
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
