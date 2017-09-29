package com.winx.river.impl.pool;

import java.lang.annotation.*;
import java.util.concurrent.*;

/**
 * @author wangwenxiang
 * @create 2017-05-26.
 */
@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Inherited
public @interface ThreadPool {

    /**
     * thread name prefix
     */
    String name() default "";

    /**
     * the same group will use the same one executor
     */
    String group() default "";

    Class<ThreadFactory> threadFactory() default ThreadFactory.class;

    /**
     * the number of threads to keep in the pool
     */
    int corePoolSize();

    /**
     * the maximum number of threads to allow in the pool
     */
    int maximumPoolSize();

    /**
     * when the number of threads is greater than the core,
     * this is the maximum time that excess idle threads
     * will wait for new tasks before terminating.
     */
    long keepAliveTime();

    /**
     * the time unit for the keepAliveTime argument
     */
    TimeUnit timeUnit() default TimeUnit.SECONDS;

    /**
     * the queue to use for holding tasks before they are executed.
     * This queue will hold only the Runnable
     * tasks submitted by the execute method.
     */
    Class<? extends BlockingQueue> workQueue() default LinkedBlockingQueue.class;

    /**
     * the handler to use when execution is blocked
     * because the thread bounds and queue capacities are reached
     */
    Class<RejectedExecutionHandler> handler() default RejectedExecutionHandler.class;
}
