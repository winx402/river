package com.github.winx402.river.impl.aop;

import java.lang.annotation.*;

/**
 * @author wangwenxiang
 * aop the method
 */
@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Inherited
public @interface Aop {
    Class<? extends AopPoint> value();
}
