package com.winx.river.exception;

/**
 * @author wangwenxiang
 * @create 2017-05-21.
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
