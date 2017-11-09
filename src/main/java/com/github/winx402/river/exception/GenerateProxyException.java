package com.github.winx402.river.exception;

/**
 * @author wangwenxiang
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
