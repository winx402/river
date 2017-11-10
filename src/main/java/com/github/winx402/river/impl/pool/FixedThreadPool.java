package com.github.winx402.river.impl.pool;

import java.lang.annotation.*;
import java.util.concurrent.ThreadFactory;

/**
 * @author wangwenxiang
 */
@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Inherited
public @interface FixedThreadPool {

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
     * thread num
     * @return threads
     */
    int threads();

    /**
     * @return threadFactory
     */
    Class<ThreadFactory> threadFactory() default ThreadFactory.class;


}
