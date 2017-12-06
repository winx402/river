package com.github.winx402.river.impl.cache;

import com.github.winx402.river.base.BaseInterceptor;
import com.github.winx402.river.util.CollectionUtil;
import com.google.common.base.Objects;
import com.google.common.cache.LoadingCache;

import java.lang.reflect.Method;
import java.util.Collection;
import java.util.concurrent.ConcurrentMap;

import static com.github.winx402.river.base.FunctionDistribute.baseInterceptorMap;

/**
 * @author didi
 */
public class CacheHandler {

    private static LoadingCache<LocalCacheTemplate.Key, Object> getLoadingCache(Method method){
        CacheInterceptor cacheInterceptor = (CacheInterceptor) baseInterceptorMap.get(Cache.class);
        return cacheInterceptor.getLocalCacheTemplateCache(method).loadingCache;
    }

    public static Object remove(Method method, Object[] keys) {
        LoadingCache<LocalCacheTemplate.Key, Object> cacheFromMap = getLoadingCache(method);
        if (cacheFromMap == null) {
            return null;
        }
        if (keys == null) {
            keys = new Object[0];
        }
        LocalCacheTemplate.Key key = new LocalCacheTemplate.Key(method, keys);
        Object o = null;
        try {
            o = cacheFromMap.get(key);
        } catch (Exception e) {
            //do nothing
        }
        cacheFromMap.invalidate(key);
        return o;
    }

    public static void clear(Method method) {
        LoadingCache<LocalCacheTemplate.Key, Object> cacheFromMap = getLoadingCache(method);
        if (cacheFromMap == null) {
            return;
        }
        ConcurrentMap<LocalCacheTemplate.Key, Object> keyObjectConcurrentMap = cacheFromMap.asMap();
        if (CollectionUtil.isEmpty(keyObjectConcurrentMap)) return;
        for (LocalCacheTemplate.Key key : keyObjectConcurrentMap.keySet()){
            if (Objects.equal(key.getMethod(), method)){
                cacheFromMap.invalidate(key);
            }
        }
    }

    public static void clearAll() {
        CacheInterceptor cacheInterceptor = (CacheInterceptor) baseInterceptorMap.get(Cache.class);
        Collection<LocalCacheTemplate> localCacheTemplateCaches = cacheInterceptor.getLocalCacheTemplateCaches();
        if (CollectionUtil.isEmpty(localCacheTemplateCaches)) return;
        for (LocalCacheTemplate localCacheTemplate : localCacheTemplateCaches){
            if (localCacheTemplate.loadingCache == null) return;
            localCacheTemplate.loadingCache.invalidateAll();
        }
    }
}
