package com.github.winx402.river.exception;

/**
 * @author wangwenxiang
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
