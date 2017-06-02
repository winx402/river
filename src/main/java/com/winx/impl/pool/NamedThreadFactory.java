package com.winx.impl.pool;

import java.util.concurrent.ThreadFactory;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author wangwenxiang
 * @create 2017-05-26.
 */
public class NamedThreadFactory implements ThreadFactory {
    private final AtomicInteger threadNum;
    private final String prefix;
    private final boolean daemo;
    private final ThreadGroup group;

    public NamedThreadFactory(String prefix) {
        this(prefix, true);
    }

    public NamedThreadFactory(String prefix, boolean daemo) {
        this.threadNum = new AtomicInteger(1);
        this.prefix = prefix + "-thread-";
        this.daemo = daemo;
        SecurityManager s = System.getSecurityManager();
        this.group = s == null?Thread.currentThread().getThreadGroup():s.getThreadGroup();
    }

    public Thread newThread(Runnable runnable) {
        String name = this.prefix + this.threadNum.getAndIncrement();
        Thread ret = new Thread(this.group, runnable, name, 0L);
        ret.setDaemon(this.daemo);
        return ret;
    }

    public ThreadGroup getThreadGroup() {
        return this.group;
    }
}
