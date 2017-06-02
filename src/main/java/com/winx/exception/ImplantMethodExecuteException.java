package com.winx.exception;

/**
 * @author wangwenxiang
 * @create 2017-05-14.
 */
public class ImplantMethodExecuteException extends RuntimeException {
    public ImplantMethodExecuteException() {
        super();
    }

    public ImplantMethodExecuteException(String message) {
        super(message);
    }

    public ImplantMethodExecuteException(String message, Throwable cause) {
        super(message, cause);
    }

    public ImplantMethodExecuteException(Throwable cause) {
        super(cause);
    }
}
