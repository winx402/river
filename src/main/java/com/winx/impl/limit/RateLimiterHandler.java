package com.winx.impl.limit;

import com.google.common.util.concurrent.RateLimiter;

import java.util.concurrent.TimeUnit;

/**
 * @author wangwenxiang
 * @create 2017-05-24.
 */
public class RateLimiterHandler {

    private RateLimiter rateLimiter;

    private long timeOut;

    public RateLimiterHandler(com.google.common.util.concurrent.RateLimiter rateLimiter, long timeOut) {
        this.rateLimiter = rateLimiter;
        this.timeOut = timeOut;
    }

    public boolean tryAcquire(){
        if (timeOut <= 0){
            rateLimiter.acquire();
        }else {
            if (rateLimiter.tryAcquire(1, timeOut, TimeUnit.SECONDS)){
                rateLimiter.acquire();
            }else {
                return false;
            }
        }
        return true;
    }
}
