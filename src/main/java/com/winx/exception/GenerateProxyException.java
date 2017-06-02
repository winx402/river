package com.winx.exception;

/**
 * @author wangwenxiang
 * @create 2017-05-21.
 */
public class GenerateProxyException extends RuntimeException {
    public GenerateProxyException() {
        super();
    }

    public GenerateProxyException(String message) {
        super(message);
    }

    public GenerateProxyException(String message, Throwable cause) {
        super(message, cause);
    }

    public GenerateProxyException(Throwable cause) {
        super(cause);
    }
}
