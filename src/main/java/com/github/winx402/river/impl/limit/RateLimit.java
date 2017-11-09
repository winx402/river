package com.github.winx402.river.impl.limit;

import java.lang.annotation.*;
import java.util.concurrent.TimeUnit;

/**
 * @author wangwenxiang
 */
@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
public @interface RateLimit {
    /**
     * rate limit per second
     * @return permitsPerSecond
     */
    double permitsPerSecond();

    /**
     * warm up duration
     * @return warmupPeriod
     */
    long warmupPeriod() default 0L;

    String group() default "";

    /**
     * it will take effect when timeOut less than 0
     * @return timeOut
     */
    long timeOut() default 0L;
}
