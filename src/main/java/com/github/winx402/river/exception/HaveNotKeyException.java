package com.github.winx402.river.exception;

/**
 * @author wangwenxiang
 */
public class HaveNotKeyException extends RuntimeException {
    public HaveNotKeyException() {
        super();
    }

    public HaveNotKeyException(String message) {
        super(message);
    }

    public HaveNotKeyException(String message, Throwable cause) {
        super(message, cause);
    }

    public HaveNotKeyException(Throwable cause) {
        super(cause);
    }
}
