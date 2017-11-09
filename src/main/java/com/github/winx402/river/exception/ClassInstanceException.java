package com.github.winx402.river.exception;

/**
 * @author wangwenxiang
 * @create 2017-05-14.
 */
public class ClassInstanceException extends RuntimeException {
    public ClassInstanceException() {
        super();
    }

    public ClassInstanceException(String message) {
        super(message);
    }

    public ClassInstanceException(String message, Throwable cause) {
        super(message, cause);
    }
}
