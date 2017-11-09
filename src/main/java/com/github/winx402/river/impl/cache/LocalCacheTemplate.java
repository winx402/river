package com.github.winx402.river.impl.cache;

import com.github.winx402.river.base.MethodPointerHandler;
import com.github.winx402.river.exception.HaveNotKeyException;
import com.google.common.base.Optional;
import com.google.common.cache.LoadingCache;
import com.github.winx402.river.exception.ImplantMethodExecuteException;
import com.github.winx402.river.exception.InterruptProcessException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Method;
import java.util.Arrays;

/**
 * @author wangwenxiang
 */
public class LocalCacheTemplate extends MethodPointerHandler.MethodPointerLimit7 {

    /**
     * parse the cache key from method params
     */
    private AbstractKeyParsing keyParsing;

    private static final Logger log = LoggerFactory.getLogger(LocalCacheTemplate.class);

    /**
     * local cache object
     * guava cache
     */
    private LoadingCache<Key, Object> loadingCache;

    static final Optional ABSENT = Optional.absent();

    static final String EMPTY = "";

    private LocalCacheTemplate() {
    }

    static LocalCacheTemplate newLocalCache(Method method) {
        LocalCacheTemplate localCacheTemplate = new LocalCacheTemplate();
        localCacheTemplate.loadingCache = LocalCacheBuilder.build(method);
        localCacheTemplate.keyParsing = AbstractKeyParsing.newBuilder().builder(method);
        return localCacheTemplate;
    }

    Object getResult(Object[] params) {
        Key paramsKey = keyParsing.getParamsKey(params);
        if (paramsKey == null) {
            throw new HaveNotKeyException("cache key parsing failed, params : " + Arrays.toString(params));
        }
        paramsKey.setMethod(getMethod());
        Object object;
        try {
            object = loadingCache.get(paramsKey);
        } catch (Exception e) {
            if (e.getCause() instanceof InterruptProcessException)
                object = getResult();
            else
                throw new ImplantMethodExecuteException(e);
        }
        return ABSENT.equals(object) ? paramsKey.cacheOptions.getDefaultResult() : object;
    }

    /**
     * key object
     */
    static class Key {

        private Method method;

        private Object[] objectKeys;

        CacheOptions cacheOptions;

        Key(Object[] objectKeys, CacheOptions cacheOptions) {
            this.objectKeys = objectKeys;
            this.cacheOptions = cacheOptions;
        }

        public Object[] getObjectKeys() {
            return objectKeys;
        }

        public void setMethod(Method method) {
            this.method = method;
        }

        public Method getMethod() {
            return method;
        }

        @Override
        public boolean equals(Object o) {
            if (!this.cacheOptions.isUseCache()) return false;
            if (this == o) return true;
            if (!(o instanceof Key)) return false;

            Key key = (Key) o;
            return (method != null ? method.equals(key.method) : key.method == null) && Arrays.equals(objectKeys, key.objectKeys);
        }

        @Override
        public int hashCode() {
            int result = method != null ? method.hashCode() : 0;
            result = 31 * result + Arrays.hashCode(objectKeys);
            return result;
        }

        @Override
        public String toString() {
            return "Key{" +
                    "objectKeys=" + Arrays.toString(objectKeys) +
                    '}';
        }
    }

}
