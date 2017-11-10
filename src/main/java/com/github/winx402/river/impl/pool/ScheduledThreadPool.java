package com.github.winx402.river.impl.pool;

import java.lang.annotation.*;
import java.util.concurrent.ThreadFactory;

/**
 * @author wangwenxiang
 */
@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Inherited
public @interface ScheduledThreadPool {
    /**
     * thread name prefix
     * @return name
     */
    String name() default "";

    /**
     * the same group will use the same one executor
     * @return group
     */
    String group() default "";

    /**
     * @return coreSize
     */
    int coreSize() default 1;

    /**
     * @return threadFactory
     */
    Class<ThreadFactory> threadFactory() default ThreadFactory.class;
}
