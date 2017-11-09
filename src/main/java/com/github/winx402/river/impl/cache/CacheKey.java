package com.github.winx402.river.impl.cache;

import java.lang.annotation.*;
import java.util.concurrent.TimeUnit;

/**
 * @author wangwenxiang
 */
@Target({ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
public @interface CacheKey {

    /**
     * 默认不填写时，表示整个对象作为key
     * 填写具体值时表示该对象内的属性
     * @return name
     */
    String[] name() default {};
}
