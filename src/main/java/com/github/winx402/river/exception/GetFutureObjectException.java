package com.github.winx402.river.exception;

/**
 * @author wangwenxiang
 */
public class GetFutureObjectException extends RuntimeException {
    public GetFutureObjectException() {
        super();
    }

    public GetFutureObjectException(String message) {
        super(message);
    }

    public GetFutureObjectException(Throwable throwable){
        super(throwable);
    }

    public GetFutureObjectException(String message, Throwable cause) {
        super(message, cause);
    }
}
