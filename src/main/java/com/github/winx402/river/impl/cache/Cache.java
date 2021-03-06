package com.github.winx402.river.impl.cache;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.util.concurrent.TimeUnit;


/**
 * @author wangwenxiang
 * aop the method
 */
@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
public @interface Cache {
    int[] keyIndex() default {};

    TimeUnit timeUnit() default TimeUnit.SECONDS;

    long timeOut() default -1;

    int maxSize() default -1;

    Class<? extends CacheKeyGetter> keyGetter() default AbstractKeyParsing.DefaultKeyGetter.class;
}
