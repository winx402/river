package com.winx.river.impl.limit;

import com.winx.river.base.AbstractExcuteRelation;
import com.winx.river.exception.RateLimitTimeOutException;

import java.lang.reflect.Method;

/**
 * @author wangwenxiang
 * @create 2017-05-24.
 */
public class RateLimitInterceptor extends AbstractExcuteRelation<Method, RateLimiterHandler> {

    protected RateLimiterHandler getBindObject() {
        return getValue(getMethod());
    }

    protected Object implant() {
        RateLimiterHandler rateLimiter = getBindObject();
        if (rateLimiter.tryAcquire()){
            return doInvoke();
        }
        throw new RateLimitTimeOutException("rate limit time out");
    }

    public void parsingMethod(Method method) {
        RateLimiterHandler rateLimiter = RateLimitBuilder.build(method);
        bind(method, rateLimiter);
    }
}