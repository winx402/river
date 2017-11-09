package com.github.winx402.river.impl.limit;

import com.google.common.base.Preconditions;
import com.google.common.base.Strings;
import com.google.common.collect.Maps;
import com.google.common.util.concurrent.RateLimiter;

import java.lang.reflect.Method;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * @author wangwenxiang
 * @create 2017-05-24.
 */
class RateLimitBuilder {
    private static Map<String, RateLimiterHandler> rateLimiterMap = Maps.newHashMap();

    static RateLimiterHandler build(Method method) {
        Preconditions.checkNotNull(method, "method is null");
        RateLimit rateLimit = method.getAnnotation(RateLimit.class);
        Preconditions.checkNotNull(rateLimit, "annotation rateLimit is null");
        String group = rateLimit.group();
        if (Strings.isNullOrEmpty(group)) group = method.getName();
        RateLimiterHandler rateLimiterHandler = rateLimiterMap.get(group);
        if (rateLimiterHandler != null) return rateLimiterHandler;
        double v = rateLimit.permitsPerSecond();
        long l = rateLimit.warmupPeriod();
        Preconditions.checkArgument(v > 0.0, "limit count must be greater than 0");
        Preconditions.checkArgument(l >= 0L, "warm up time must be  greater than or equal to 0");
        RateLimiter rateLimiter = RateLimiter.create(v, l, TimeUnit.SECONDS);
        rateLimiterHandler = new RateLimiterHandler(rateLimiter, rateLimit.timeOut());
        rateLimiterMap.put(group, rateLimiterHandler);
        return rateLimiterHandler;
    }
}
