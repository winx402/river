package com.github.winx402.river.impl.aop;

import java.lang.annotation.*;

/**
 * @author wangwenxiang
 * @create 2017-05-12.
 * aop the method
 */
@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Inherited
public @interface Aop {
    Class<? extends AopPoint> value();
}
