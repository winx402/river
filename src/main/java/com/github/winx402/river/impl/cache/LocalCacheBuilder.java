package com.github.winx402.river.impl.cache;

import com.github.winx402.river.base.MethodPointerHandler;
import com.github.winx402.river.exception.InterruptProcessException;
import com.google.common.base.Preconditions;
import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import com.google.common.collect.Maps;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * @author wangwenxiang
 * @create 2017-05-17.
 */
public class LocalCacheBuilder extends MethodPointerHandler.MethodPointerLimit4 {

    private static final Logger log = LoggerFactory.getLogger(LocalCacheBuilder.class);

    private static final Map<CacheDescription, LoadingCache<LocalCacheTemplate.Key, Object>> CACHE_MAP = Maps.newHashMap();

    static LoadingCache<LocalCacheTemplate.Key, Object> build(Method method) {
        Preconditions.checkNotNull(method, "method is null");
        Cache annotation = method.getAnnotation(Cache.class);
        Preconditions.checkNotNull(annotation, "cache annotation is null");
        return getCache(annotation);
    }

    private static LoadingCache<LocalCacheTemplate.Key, Object> getCache(Cache cache) {
        CacheDescription cacheDescription = CacheDescription.fromCacheAnnotation(cache);
        LoadingCache<LocalCacheTemplate.Key, Object> keyObjectLoadingCache = CACHE_MAP.get(cacheDescription);
        if (keyObjectLoadingCache == null){
            keyObjectLoadingCache = build(cacheDescription);
            CACHE_MAP.put(cacheDescription, keyObjectLoadingCache);
        }
        return keyObjectLoadingCache;
    }

    private static LoadingCache<LocalCacheTemplate.Key, Object> build(CacheDescription cacheDescription) {
        int i = cacheDescription.getMaxSize();
        long i1 = cacheDescription.getTimeOutNanos();
        CacheBuilder<Object, Object> objectObjectCacheBuilder = CacheBuilder.newBuilder();
        if (i > 0) {
            objectObjectCacheBuilder.maximumSize(i);
        }
        if (i1 > 0) {
            objectObjectCacheBuilder.expireAfterWrite(i1, TimeUnit.NANOSECONDS);
        }
        return objectObjectCacheBuilder.build(new CacheLoader<LocalCacheTemplate.Key, Object>() {
            @Override
            public Object load(LocalCacheTemplate.Key key) throws InterruptProcessException {
                Object result = isNull(doInvoke());
                if (!key.cacheOptions.isAsCache()) {
                    setResult(result);
                    log.debug("get result not from cache, method:{}, params: {}", key.getMethod().getName(), Arrays.toString(key.getObjectKeys()));
                    throw new InterruptProcessException();
                }
                log.debug("get result from cache, method:{}, params: {}", key.getMethod().getName(), Arrays.toString(key.getObjectKeys()));
                return result;
            }
        });
    }

    private static Object isNull(Object object) {
        return object == null ? LocalCacheTemplate.ABSENT : object;
    }

    private static class CacheDescription {
        private long timeOutNanos;

        private int maxSize;

        static CacheDescription fromCacheAnnotation(Cache cache) {
            CacheDescription cacheDescription = new CacheDescription();
            cacheDescription.maxSize = cache.maxSize();
            long i = cache.timeOut();
            if (i == -1) cacheDescription.timeOutNanos = -1;
            else cacheDescription.timeOutNanos = cache.timeUnit().toNanos(i);
            return cacheDescription;
        }

        long getTimeOutNanos() {
            return timeOutNanos;
        }

        int getMaxSize() {
            return maxSize;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (!(o instanceof CacheDescription)) return false;

            CacheDescription that = (CacheDescription) o;

            if (timeOutNanos != that.timeOutNanos) return false;
            return maxSize == -1 && that.maxSize == -1;
        }

        @Override
        public int hashCode() {
            int result = (int) (timeOutNanos ^ (timeOutNanos >>> 32));
            result = 31 * result + maxSize;
            return result;
        }
    }

}
