package com.winx.impl.pool;

import java.lang.annotation.*;
import java.util.concurrent.ThreadFactory;

/**
 * @author wangwenxiang
 * @create 2017-05-26.
 */
@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Inherited
public @interface SingleThreadExecutor {
    /**
     * thread name prefix
     */
    String name() default "";

    /**
     * the same group will use the same one executor
     */
    String group() default "";

    Class<ThreadFactory> threadFactory() default ThreadFactory.class;
}
