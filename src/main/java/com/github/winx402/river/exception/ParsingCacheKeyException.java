package com.github.winx402.river.exception;

/**
 * @author wangwenxiang
 * @create 2017-05-21.
 */
public class ParsingCacheKeyException extends RuntimeException {
    public ParsingCacheKeyException() {
        super();
    }

    public ParsingCacheKeyException(String message) {
        super(message);
    }

    public ParsingCacheKeyException(String message, Throwable cause) {
        super(message, cause);
    }

    public ParsingCacheKeyException(Throwable cause) {
        super(cause);
    }
}
