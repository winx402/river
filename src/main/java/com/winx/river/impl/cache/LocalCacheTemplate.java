package com.winx.river.impl.cache;

import com.google.common.base.Optional;
import com.google.common.cache.LoadingCache;
import com.winx.river.exception.ImplantMethodExecuteException;

import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.concurrent.ExecutionException;

/**
 * @author wangwenxiang
 * @create 2017-05-16.
 */
public class LocalCacheTemplate {

    /**
     * parse the cache key from method params
     */
    private AbstractKeyParsing keyParsing;

    /**
     * local cache object
     * guava cache
     */
    private LoadingCache<Key, Object> loadingCache;

    static final Optional ABSENT = Optional.absent();

    private LocalCacheTemplate() {
    }

    static LocalCacheTemplate newLocalCache(Method method) {
        LocalCacheTemplate localCacheTemplate = new LocalCacheTemplate();
        localCacheTemplate.loadingCache = LocalCacheBuilder.build(method);
        localCacheTemplate.keyParsing = AbstractKeyParsing.newBuilder().builder(method);
        return localCacheTemplate;
    }

    public Object getResult(Object[] params) {
        try {
            Object object = loadingCache.get(keyParsing.getParamsKey(params));
            return ABSENT.equals(object) ? null : object;
        } catch (ExecutionException e) {
            throw new ImplantMethodExecuteException("get value from local cache error", e);
        }
    }

    /**
     * key object
     */
    static class Key {

        private Object[] objectKeys;

        private boolean cacheFirst = true;

        Key(Object[] objects) {
            this.objectKeys = objects;
        }

        Key(Object[] objectKeys, boolean cacheFirst) {
            this.objectKeys = objectKeys;
            this.cacheFirst = cacheFirst;
        }

        @Override
        public boolean equals(Object o) {
            if (!this.cacheFirst) return false;
            if (this == o) return true;
            if (!(o instanceof Key)) return false;

            Key key = (Key) o;

//         Probably incorrect - comparing Object[] arrays with Arrays.equals
            return Arrays.equals(objectKeys, key.objectKeys);
        }

        @Override
        public int hashCode() {
            return Arrays.hashCode(objectKeys);
        }

        @Override
        public String toString() {
            return "Key{" +
                    "objectKeys=" + Arrays.toString(objectKeys) +
                    '}';
        }
    }

}
