package com.github.winx402.river.impl.pool;

import java.lang.annotation.*;
import java.util.concurrent.ThreadFactory;

/**
 * @author wangwenxiang
 */
@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
public @interface CachedThreadPool{
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
