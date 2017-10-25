package com.winx.river.impl.limit;

import com.google.common.util.concurrent.RateLimiter;

import java.util.concurrent.TimeUnit;

/**
 * @author wangwenxiang
 * @create 2017-05-24.
 */
class RateLimiterHandler {

    private RateLimiter rateLimiter;

    private long timeOut;

    RateLimiterHandler(RateLimiter rateLimiter, long timeOut) {
        this.rateLimiter = rateLimiter;
        this.timeOut = timeOut;
    }

    boolean tryAcquire() {
        if (timeOut <= 0) {
            rateLimiter.acquire();
        } else {
            if (rateLimiter.tryAcquire(1, timeOut, TimeUnit.SECONDS)) {
                rateLimiter.acquire();
            } else {
                return false;
            }
        }
        return true;
    }
}
