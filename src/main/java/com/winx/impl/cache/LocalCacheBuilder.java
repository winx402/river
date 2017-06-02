package com.winx.impl.cache;

import com.google.common.base.Preconditions;
import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import com.winx.base.MethodPointerHandler;

import java.lang.reflect.Method;
import java.util.concurrent.TimeUnit;

/**
 * @author wangwenxiang
 * @create 2017-05-17.
 */
public class LocalCacheBuilder extends MethodPointerHandler.MethodPointerLimit4 {

    public static LoadingCache<LocalCacheTemplate.Key, Object> build(Method method){
        Preconditions.checkNotNull(method, "method is null");
        Cache annotation = method.getAnnotation(Cache.class);
        Preconditions.checkNotNull(annotation, "cache annotation is null");
        return build(annotation);
    }

    private static LoadingCache<LocalCacheTemplate.Key, Object> build(Cache cache){
        int i = cache.maxSize();
        int i1 = cache.timeOut();
        TimeUnit timeUnit = cache.timeUnit();
        CacheBuilder<Object, Object> objectObjectCacheBuilder = CacheBuilder.newBuilder();
        if (i > 0){
            objectObjectCacheBuilder.maximumSize(i);
        }
        if (i1 > 0){
            objectObjectCacheBuilder.expireAfterWrite(i1, timeUnit);
        }
        return objectObjectCacheBuilder.build(new CacheLoader<LocalCacheTemplate.Key, Object>() {
            @Override
            public Object load(LocalCacheTemplate.Key key) throws Exception {
                return isNull(doInvoke());
            }
        });
    }

    private static Object isNull(Object object){
        return object == null ? LocalCacheTemplate.ABSENT : object;
    }

}
