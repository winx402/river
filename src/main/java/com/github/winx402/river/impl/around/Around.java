package com.github.winx402.river.impl.around;

import java.lang.annotation.*;

/**
 * @author wangwenxiang
 * aop the method
 */
@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Inherited
public @interface Around {
    Class<? extends AroundPoint> value();
}
