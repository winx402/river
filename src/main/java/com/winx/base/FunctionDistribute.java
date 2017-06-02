package com.winx.base;

import com.google.common.collect.Sets;
import com.winx.impl.aop.Aop;
import com.winx.impl.aop.AopInterceptor;
import com.winx.impl.around.Around;
import com.winx.impl.around.AroundInterceptor;
import com.winx.impl.cache.Cache;
import com.winx.impl.cache.CacheInterceptor;
import com.winx.impl.limit.RateLimit;
import com.winx.impl.limit.RateLimitInterceptor;
import com.winx.impl.pool.*;
import com.winx.util.CollectionUtil;
import net.sf.cglib.proxy.Callback;
import net.sf.cglib.proxy.NoOp;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.*;

/**
 * @author wangwenxiang
 * @create 2017-05-13.
 * 识别注解，参数绑定
 */
public class FunctionDistribute {

    private static final BaseInterceptor threadPoolInterceptor = new ThreadPoolInterceptor();

    private static Map<Class<? extends Annotation>, BaseInterceptor> baseInterceptorMap = new HashMap<Class<? extends Annotation>, BaseInterceptor>(){{
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

    public static Callback[] getBaseInterceptorSet() {
        return baseInterceptorSet;
    }

    static {
        annotationSet = baseInterceptorMap.keySet();
        baseInterceptorSet = new Callback[]{new NoOp(){},baseInterceptorMap.get(Aop.class),baseInterceptorMap.get(Around.class),baseInterceptorMap.get(Cache.class), baseInterceptorMap.get(RateLimit.class), baseInterceptorMap.get(ThreadPool.class), baseInterceptorMap.get(CachedThreadPool.class), baseInterceptorMap.get(FixedThreadPool.class), baseInterceptorMap.get(ScheduledThreadPool.class), baseInterceptorMap.get(SingleThreadExecutor.class), baseInterceptorMap.get(SingleThreadScheduledExecutor.class)};
    }

    public static boolean distributeAndBind(Class c){
        Method[] methods = c.getMethods();
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
        if (annotations == null || annotations.length == 0){
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

    public static Set<Class<? extends Annotation>> getAnnotationSet() {
        return annotationSet;
    }

    private static void bind(Method method, BaseInterceptor baseInterceptor){
        baseInterceptor.parsingMethod(method);
    }

    public static int indexOfAnnotation(Class<? extends Annotation> ann){
        if (ann == null || !annotationSet.contains(ann)){
            return 0;
        }
        BaseInterceptor baseInterceptor = baseInterceptorMap.get(ann);
        for (int i = 0; i < baseInterceptorSet.length; i++){
            if (baseInterceptorSet[i] == baseInterceptor){
                return i;
            }
        }
        return 0;
    }

}
