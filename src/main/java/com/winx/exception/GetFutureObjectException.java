package com.winx.exception;

/**
 * @author wangwenxiang
 * @create 2017-05-14.
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
