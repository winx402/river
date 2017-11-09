package com.github.winx402.river.impl.limit;

import java.lang.annotation.*;
import java.util.concurrent.TimeUnit;

/**
 * @author wangwenxiang
 * @create 2017-05-24.
 */
@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
public @interface RateLimit {
    /**
     * rate limit per second
     */
    double permitsPerSecond();

    /**
     * warm up duration
     */
    long warmupPeriod() default 0L;

    String group() default "";

    /**
     * it will take effect when timeOut > 0
     */
    long timeOut() default 0L;
}
