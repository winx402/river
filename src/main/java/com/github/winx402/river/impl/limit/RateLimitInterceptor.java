package com.github.winx402.river.impl.limit;

import com.github.winx402.river.base.AbstractExcuteRelation;
import com.github.winx402.river.exception.RateLimitTimeOutException;

import java.lang.reflect.Method;

/**
 * @author wangwenxiang
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