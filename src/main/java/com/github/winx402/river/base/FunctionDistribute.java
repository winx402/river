package com.github.winx402.river.base;

import com.github.winx402.river.impl.aop.Aop;
import com.github.winx402.river.impl.aop.AopInterceptor;
import com.github.winx402.river.impl.around.Around;
import com.github.winx402.river.impl.around.AroundInterceptor;
import com.github.winx402.river.impl.cache.Cache;
import com.github.winx402.river.impl.cache.CacheInterceptor;
import com.github.winx402.river.impl.limit.RateLimit;
import com.github.winx402.river.impl.limit.RateLimitInterceptor;
import com.github.winx402.river.impl.pool.*;
import com.github.winx402.river.util.CollectionUtil;
import com.google.common.collect.Sets;
import net.sf.cglib.proxy.Callback;
import net.sf.cglib.proxy.NoOp;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.*;

/**
 * @author wangwenxiang
 * 识别注解，参数绑定
 */
public class FunctionDistribute {

    private static final BaseInterceptor threadPoolInterceptor = new ThreadPoolInterceptor();

    public static final Map<Class<? extends Annotation>, BaseInterceptor> baseInterceptorMap = new HashMap<Class<? extends Annotation>, BaseInterceptor>(){{
        put(Aop.class, new AopInterceptor());
        put(Around.class, new AroundInterceptor());
        put(Cache.class, new CacheInterceptor());
        put(RateLimit.class, new RateLimitInterceptor());
        put(ThreadPool.class, threadPoolInterceptor);
        put(CachedThreadPool.class, threadPoolInterceptor);
        put(FixedThreadPool.class, threadPoolInterceptor);
        put(ScheduledThreadPool.class, threadPoolInterceptor);
        put(SingleThreadExecutor.class, threadPoolInterceptor);
        put(SingleThreadScheduledExecutor.class, threadPoolInterceptor);
    }};

    private static Set<Class<? extends Annotation>> annotationSet;

    private static Callback[] baseInterceptorSet;

    static Callback[] getBaseInterceptorSet() {
        return baseInterceptorSet;
    }

    static {
        annotationSet = baseInterceptorMap.keySet();
        baseInterceptorSet = new Callback[]{new NoOp(){},baseInterceptorMap.get(Aop.class),baseInterceptorMap.get(Around.class),baseInterceptorMap.get(Cache.class), baseInterceptorMap.get(RateLimit.class), baseInterceptorMap.get(ThreadPool.class), baseInterceptorMap.get(CachedThreadPool.class), baseInterceptorMap.get(FixedThreadPool.class), baseInterceptorMap.get(ScheduledThreadPool.class), baseInterceptorMap.get(SingleThreadExecutor.class), baseInterceptorMap.get(SingleThreadScheduledExecutor.class)};
    }

    static boolean distributeAndBind(Class c){
        Method[] methods = c.getDeclaredMethods();
        boolean isProxy = false;
        for (Method method : methods){
            Annotation[] annotations = method.getAnnotations();
            Set<BaseInterceptor> baseInterceptors = findInterceptor(annotations);
            if (CollectionUtil.isNotEmpty(baseInterceptors)){
                bind(method, baseInterceptors.iterator().next());
                isProxy = true;
            }
        }
        return isProxy;
    }

    private static Set<BaseInterceptor> findInterceptor(Annotation[] annotations){
        if (CollectionUtil.isEmpty(annotations)){
            return null;
        }
        Set<BaseInterceptor> set = Sets.newLinkedHashSet();
        for (Annotation annotation : annotations){
            if (annotationSet.contains(annotation.annotationType())){
                set.add(baseInterceptorMap.get(annotation.annotationType()));
            }
        }
        return set;
    }

    static Set<Class<? extends Annotation>> getAnnotationSet() {
        return annotationSet;
    }

    private static void bind(Method method, BaseInterceptor baseInterceptor){
        baseInterceptor.parsingMethod(method);
    }

    static int indexOfAnnotation(Class<? extends Annotation> ann){
        BaseInterceptor baseInterceptor = baseInterceptorMap.get(ann);
        for (int i = 0; i < baseInterceptorSet.length; i++){
            if (baseInterceptorSet[i] == baseInterceptor){
                return i;
            }
        }
        return ConcreteClassCallbackFilter.DEFAULT_METHOD_INDEX;
    }

}
