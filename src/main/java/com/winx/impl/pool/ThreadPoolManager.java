package com.winx.impl.pool;

import com.google.common.base.Preconditions;
import com.google.common.base.Strings;
import com.google.common.collect.Maps;
import com.winx.base.ClassInstanceTool;
import com.winx.util.CollectionUtil;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.*;

/**
 * @author wangwenxiang
 * @create 2017-05-26.
 */
public class ThreadPoolManager {

    private static final Map<String, ExecutorService> EXECUTOR_MAP = Maps.newHashMap();

    private static final Map<Class<? extends Annotation>, ParsingAnnotation2Executor> PARSER_MAP = new HashMap<Class<? extends Annotation>, ParsingAnnotation2Executor>() {{
        put(CachedThreadPool.class, new CachedThreadPoolParser());
        put(FixedThreadPool.class, new FixedThreadPoolParser());
        put(ScheduledThreadPool.class, new ScheduledThreadPoolParser());
        put(SingleThreadExecutor.class, new SingleThreadPoolParser());
        put(SingleThreadScheduledExecutor.class, new SingleThreadScheduledPoolParser());
        put(ThreadPool.class, new ThreadPoolParser());
    }};

    private static final Set<Class<? extends Annotation>> THREAD_POOL_ANNOTATION_ARRAY = PARSER_MAP.keySet();

    public static ExecutorService buildExecutor(Method method) {
        Preconditions.checkNotNull(method);
        Annotation[] annotations = method.getAnnotations();
        Annotation threadPoolAnnotation = findThreadPoolAnnotation(annotations);
        Preconditions.checkNotNull(threadPoolAnnotation);
        return parsingAnnotation(threadPoolAnnotation);
    }

    private static ExecutorService parsingAnnotation(Annotation annotation) {
        ParsingAnnotation2Executor parsingAnnotation2Executor = PARSER_MAP.get(annotation.annotationType());
        if (parsingAnnotation2Executor == null) return null;
        String group = parsingAnnotation2Executor.group(annotation);
        ExecutorService executorService = fromCache(group);
        if (executorService != null) return executorService;
        executorService = parsingAnnotation2Executor.parsing(annotation);
        tryPut(group, executorService);
        return executorService;
    }

    private static Annotation findThreadPoolAnnotation(Annotation[] annotations) {
        if (CollectionUtil.isEmpty(annotations)) return null;
        for (Annotation annotation : annotations) {
            if (THREAD_POOL_ANNOTATION_ARRAY.contains(annotation.annotationType())) {
                return annotation;
            }
        }
        return null;
    }

    private static boolean haveThreadPoolAnnotation(Annotation[] annotations) {
        return findThreadPoolAnnotation(annotations) != null;
    }

    private static ExecutorService fromCache(String group) {
        if (Strings.isNullOrEmpty(group)) return null;
        return EXECUTOR_MAP.get(group);
    }

    private static void tryPut(String group, ExecutorService executorService) {
        if (Strings.isNullOrEmpty(group) || executorService == null) return;
        EXECUTOR_MAP.put(group, executorService);
    }

    private static class CachedThreadPoolParser implements ParsingAnnotation2Executor<CachedThreadPool> {

        public String group(CachedThreadPool annotation) {
            return annotation.group();
        }

        public ExecutorService parsing(CachedThreadPool annotation) {
            Class<ThreadFactory> threadFactoryClass = annotation.threadFactory();
            if (threadFactoryClass != ThreadFactory.class) {
                return Executors.newCachedThreadPool(ClassInstanceTool.newInstance(threadFactoryClass));
            }
            String name = annotation.name();
            if (Strings.isNullOrEmpty(name)) {
                return Executors.newCachedThreadPool();
            } else {
                return Executors.newCachedThreadPool(new NamedThreadFactory(name));
            }
        }
    }

    private static class FixedThreadPoolParser implements ParsingAnnotation2Executor<FixedThreadPool> {

        public String group(FixedThreadPool annotation) {
            return annotation.group();
        }

        public ExecutorService parsing(FixedThreadPool annotation) {
            int threads = annotation.threads();
            Preconditions.checkArgument(threads > 0, "fixed thread pool core thread size must be great than 0");
            Class<ThreadFactory> threadFactoryClass = annotation.threadFactory();
            if (threadFactoryClass != ThreadFactory.class) {
                return Executors.newFixedThreadPool(threads, ClassInstanceTool.newInstance(threadFactoryClass));
            }
            String name = annotation.name();
            if (Strings.isNullOrEmpty(name)) {
                return Executors.newFixedThreadPool(threads);
            } else {
                return Executors.newFixedThreadPool(threads, new NamedThreadFactory(name));
            }
        }
    }

    private static class ScheduledThreadPoolParser implements ParsingAnnotation2Executor<ScheduledThreadPool> {

        public String group(ScheduledThreadPool annotation) {
            return annotation.group();
        }

        public ExecutorService parsing(ScheduledThreadPool annotation) {
            int coreSize = annotation.coreSize();
            Preconditions.checkArgument(coreSize > 0, "scheduled thread pool core thread size must be great than 0");
            Class<ThreadFactory> threadFactoryClass = annotation.threadFactory();
            if (threadFactoryClass != ThreadFactory.class) {
                return Executors.newScheduledThreadPool(coreSize, ClassInstanceTool.newInstance(threadFactoryClass));
            }
            String name = annotation.name();
            if (Strings.isNullOrEmpty(name)) {
                return Executors.newScheduledThreadPool(coreSize);
            } else {
                return Executors.newScheduledThreadPool(coreSize, new NamedThreadFactory(name));
            }
        }
    }

    private static class SingleThreadPoolParser implements ParsingAnnotation2Executor<SingleThreadExecutor> {

        public String group(SingleThreadExecutor annotation) {
            return annotation.group();
        }

        public ExecutorService parsing(SingleThreadExecutor annotation) {
            Class<ThreadFactory> threadFactoryClass = annotation.threadFactory();
            if (threadFactoryClass != ThreadFactory.class) {
                return Executors.newSingleThreadExecutor(ClassInstanceTool.newInstance(threadFactoryClass));
            }
            String name = annotation.name();
            if (Strings.isNullOrEmpty(name)) {
                return Executors.newSingleThreadExecutor();
            } else {
                return Executors.newSingleThreadExecutor(new NamedThreadFactory(name));
            }
        }
    }

    private static class SingleThreadScheduledPoolParser implements ParsingAnnotation2Executor<SingleThreadScheduledExecutor> {

        public String group(SingleThreadScheduledExecutor annotation) {
            return annotation.group();
        }

        public ExecutorService parsing(SingleThreadScheduledExecutor annotation) {
            Class<ThreadFactory> threadFactoryClass = annotation.threadFactory();
            if (threadFactoryClass != ThreadFactory.class) {
                return Executors.newSingleThreadScheduledExecutor(ClassInstanceTool.newInstance(threadFactoryClass));
            }
            String name = annotation.name();
            if (Strings.isNullOrEmpty(name)) {
                return Executors.newSingleThreadScheduledExecutor();
            } else {
                return Executors.newSingleThreadScheduledExecutor(new NamedThreadFactory(name));
            }
        }
    }

    private static class ThreadPoolParser implements ParsingAnnotation2Executor<ThreadPool> {

        public String group(ThreadPool annotation) {
            return annotation.group();
        }

        public ExecutorService parsing(ThreadPool annotation) {
            int corePoolSize = annotation.corePoolSize();
            int maximumPoolSize = annotation.maximumPoolSize();
            long keepAliveTime = annotation.keepAliveTime();
            TimeUnit timeUnit = annotation.timeUnit();
            Class<? extends BlockingQueue> blockingQueueClass = annotation.workQueue();
            Class<RejectedExecutionHandler> handler = annotation.handler();
            ThreadFactory threadFactory = newThreadFactory(annotation.threadFactory(), annotation.name());
            if (handler != RejectedExecutionHandler.class) {
                if (threadFactory != null) {
                    return new ThreadPoolExecutor(corePoolSize, maximumPoolSize, keepAliveTime, timeUnit, ClassInstanceTool.newInstance(blockingQueueClass), threadFactory, ClassInstanceTool.newInstance(handler));
                } else {
                    return new ThreadPoolExecutor(corePoolSize, maximumPoolSize, keepAliveTime, timeUnit, ClassInstanceTool.newInstance(blockingQueueClass), ClassInstanceTool.newInstance(handler));
                }
            }
            if (threadFactory != null) {
                return new ThreadPoolExecutor(corePoolSize, maximumPoolSize, keepAliveTime, timeUnit, ClassInstanceTool.newInstance(blockingQueueClass), threadFactory);
            } else {
                return new ThreadPoolExecutor(corePoolSize, maximumPoolSize, keepAliveTime, timeUnit, ClassInstanceTool.newInstance(blockingQueueClass));
            }
        }

        private ThreadFactory newThreadFactory(Class<ThreadFactory> threadFactoryClass, String name) {
            if (threadFactoryClass != ThreadFactory.class) return ClassInstanceTool.newInstance(threadFactoryClass);
            if (!Strings.isNullOrEmpty(name)) return new NamedThreadFactory(name);
            return null;
        }
    }
}
