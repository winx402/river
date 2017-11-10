package com.github.winx402.river.impl.pool;

import java.lang.annotation.*;
import java.util.concurrent.*;

/**
 * @author wangwenxiang
 */
@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Inherited
public @interface ThreadPool {

    /**
     * thread name prefix
     * @return name
     */
    String name() default "";

    /**
     * the same group will use the same one executor
     * @return threadFactory
     */
    String group() default "";

    Class<ThreadFactory> threadFactory() default ThreadFactory.class;

    /**
     * the number of threads to keep in the pool
     * @return corePoolSize
     */
    int corePoolSize();

    /**
     * the maximum number of threads to allow in the pool
     * @return maximumPoolSize
     */
    int maximumPoolSize();

    /**
     * when the number of threads is greater than the core,
     * this is the maximum time that excess idle threads
     * will wait for new tasks before terminating.
     * @return keepAliveTime
     */
    long keepAliveTime();

    /**
     * the time unit for the keepAliveTime argument
     * @return timeUnit
     */
    TimeUnit timeUnit() default TimeUnit.SECONDS;

    /**
     * the queue to use for holding tasks before they are executed.
     * This queue will hold only the Runnable
     * tasks submitted by the execute method.
     * @return workQueue
     */
    Class<? extends BlockingQueue> workQueue() default LinkedBlockingQueue.class;

    /**
     * the handler to use when execution is blocked
     * because the thread bounds and queue capacities are reached
     * @return handler
     */
    Class<RejectedExecutionHandler> handler() default RejectedExecutionHandler.class;
}
