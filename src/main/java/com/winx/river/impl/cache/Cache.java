package com.winx.river.impl.cache;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.util.concurrent.TimeUnit;


/**
 * @author wangwenxiang
 * @create 2017-05-12.
 * aop the method
 */
@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
public @interface Cache {
    int[] keyIndex() default {};

    TimeUnit timeUnit() default TimeUnit.SECONDS;

    int timeOut() default -1;

    int maxSize() default -1;

    Class<? extends CacheKeyGetter> keyGetter() default AbstractKeyParsing.DefaultKeyGetter.class;
}
