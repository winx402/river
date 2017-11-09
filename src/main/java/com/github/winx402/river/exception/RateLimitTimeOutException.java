package com.github.winx402.river.exception;

/**
 * @author wangwenxiang
 */
public class RateLimitTimeOutException extends RuntimeException {
    public RateLimitTimeOutException() {
        super();
    }

    public RateLimitTimeOutException(String message) {
        super(message);
    }

    public RateLimitTimeOutException(String message, Throwable cause) {
        super(message, cause);
    }
}
