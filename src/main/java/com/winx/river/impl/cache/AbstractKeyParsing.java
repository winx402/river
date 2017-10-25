package com.winx.river.impl.cache;

import com.google.common.base.Preconditions;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import com.winx.river.base.SingletonClassUtil;
import com.winx.river.exception.ParsingCacheKeyException;
import com.winx.river.util.CollectionUtil;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.*;

import static com.winx.river.impl.cache.AbstractKeyParsing.Builder.NONE_STRING;
import static com.winx.river.impl.cache.AbstractKeyParsing.Builder.TOTAL_STRING;

/**
 * @author wangwenxiang
 * @create 2017-05-17.
 */
public abstract class AbstractKeyParsing implements CacheKeyGetter {

    public abstract LocalCacheTemplate.Key getParamsKey(Object[] params);

    private static final int DEFAULT_CACHE_FIRST = -1;

    static Builder newBuilder() {
        return new Builder();
    }

    /**
     * the index of param expression get value from cache or not
     * the default value -1 show that the value from cache first
     * other value show that the index of the param which is boolean type expression whether from cache
     */
    protected int cacheFirst;

    Object[] wipeOffCacheFirst(Object[] params){
        if (cacheFirst(params)) return params;
        Object[] paramKey = new Object[params.length - 1];
        System.arraycopy(params, 0, paramKey, 0, cacheFirst);
        System.arraycopy(params, cacheFirst + 1, paramKey, cacheFirst, paramKey.length - cacheFirst);
        return paramKey;
    }

    boolean cacheFirst(Object[] params){
        if (cacheFirst == DEFAULT_CACHE_FIRST) return true;
        return (Boolean) params[cacheFirst];
    }

    public static final class DefaultKeyGetter extends AbstractKeyParsing {

        DefaultKeyGetter() {
            cacheFirst = DEFAULT_CACHE_FIRST;
        }

        public Object[] getKey(Object[] params) {
            return wipeOffCacheFirst(params);
        }

        public LocalCacheTemplate.Key getParamsKey(Object[] params) {
            Object[] key = getKey(params);
            return new LocalCacheTemplate.Key(key, cacheFirst(params));
        }
    }

    AbstractKeyParsing setCacheFirst(int cacheFirst) {
        this.cacheFirst = cacheFirst;
        return this;
    }

    /**
     * run the custom function get the Object[] as cache key
     * CacheKeyGetter from customer
     */
    private static final class CustomKeyGetter extends AbstractKeyParsing {

        private CacheKeyGetter cacheKeyGetter;

        private CustomKeyGetter(CacheKeyGetter cacheKeyGetter) {
            Preconditions.checkNotNull(cacheKeyGetter, "CacheKeyGetter is not allowed be null");
            this.cacheKeyGetter = cacheKeyGetter;
        }

        public Object[] getKey(Object[] params) {
            return cacheKeyGetter.getKey(wipeOffCacheFirst(params));
        }

        public LocalCacheTemplate.Key getParamsKey(Object[] params) {
            return new LocalCacheTemplate.Key(getKey(params), cacheFirst(params));
        }
    }

    /**
     * found the params by index
     */
    private static class IndexKeyGetter extends AbstractKeyParsing {
        private int[] paramsIndex;

        private IndexKeyGetter(int[] index) {
            paramsIndex = index;
        }

        public Object[] getKey(Object[] params) {
            Object[] result = new Object[paramsIndex.length];
            for (int i = 0; i < paramsIndex.length; i++) {
                result[i] = params[paramsIndex[i]];
            }
            return result;
        }

        public LocalCacheTemplate.Key getParamsKey(Object[] params) {
            return new LocalCacheTemplate.Key(getKey(params), cacheFirst(params));
        }
    }

    private static class PartKeyGetter extends AbstractKeyParsing {

        private Map<Integer, List<String>> paramsIndexName;

        private int length = 0;

        private PartKeyGetter(String[][] params) {
            if (CollectionUtil.isEmpty(params)) return;
            paramsIndexName = Maps.newHashMap();
            for (int i = 0; i < params.length; i++) {
                String[] param = params[i];
                if (param == NONE_STRING || param == TOTAL_STRING) continue;
                paramsIndexName.put(i, Arrays.asList(param));
                length += param.length;
            }
        }

        private static final Object[] EMPTY_OBJECT_ARRAY = new Object[0];

        public Object[] getKey(Object[] params) {
            if (CollectionUtil.isEmpty(paramsIndexName)) return EMPTY_OBJECT_ARRAY;
            Object[] result = new Object[length];
            int size = 0;
            for (Map.Entry<Integer, List<String>> entry : paramsIndexName.entrySet()) {
                Object param = params[entry.getKey()];
                Object[] keyByOneParam = getKeyByOneParam(param, entry.getValue());
                System.arraycopy(keyByOneParam, 0, result, size, keyByOneParam.length);
                size += keyByOneParam.length;
            }
            return result;
        }

        public LocalCacheTemplate.Key getParamsKey(Object[] params) {
            return new LocalCacheTemplate.Key(getKey(params), cacheFirst(params));
        }

        private Object[] getKeyByOneParam(Object param, List<String> names) {
            if (CollectionUtil.isEmpty(names)) return EMPTY_OBJECT_ARRAY;
            Object[] result = new Object[names.size()];
            try {
                for (int i = 0; i < result.length; i++) {
                    Class<?> aClass = param.getClass();
                    Field field = aClass.getDeclaredField(names.get(i));
                    field.setAccessible(true);
                    result[i] = field.get(param);
                }
            } catch (Exception e) {
                throw new ParsingCacheKeyException("parsing cache key error", e);
            }
            return result;
        }
    }

    private static class MultiKeyGetter extends AbstractKeyParsing {

        private List<AbstractKeyParsing> keyParsings;

        private MultiKeyGetter(Builder.CacheKeyRecord cacheKeyRecord) {
            keyParsings = Lists.newArrayList();
            keyParsings.add(new PartKeyGetter(cacheKeyRecord.getParamNames()));
            keyParsings.add(new IndexKeyGetter(cacheKeyRecord.getKeyIndex()));
        }

        public Object[] getKey(Object[] params) {
            Object[] result = new Object[0];
            for (AbstractKeyParsing abstractKeyParsing : keyParsings) {
                Object[] key = abstractKeyParsing.getKey(params);
                result = CollectionUtil.ArrayMerge(result, key);
            }
            return result;
        }

        public LocalCacheTemplate.Key getParamsKey(Object[] params) {
            return new LocalCacheTemplate.Key(getKey(params), cacheFirst(params));
        }
    }

    static class Builder {
        private Builder() {
        }

        static final String[] NONE_STRING = new String[]{ParamsGetterType.NONE.getValue()};
        static final String[] TOTAL_STRING = new String[]{ParamsGetterType.TOTAL.getValue()};

        AbstractKeyParsing builder(Method method) {
            Cache annotation = method.getAnnotation(Cache.class);
            Annotation[][] parameterAnnotations = method.getParameterAnnotations();
            CacheKeyRecord cacheKeyRecord = keyDefine(annotation, parameterAnnotations);
            cacheKeyRecord.setParamsType(method.getParameterTypes());
            int i = cacheKeyRecord.haveDefine();
            int cacheFirst = cacheKeyRecord.generateCacheFirst();
            switch (i) {
                case CacheKeyRecord.NONE_PARAMS:
                    if (cacheFirst == DEFAULT_CACHE_FIRST){
                        return SingletonClassUtil.newWholeKeyGetter();
                    }
                    return new DefaultKeyGetter().setCacheFirst(cacheFirst);
                case CacheKeyRecord.KEY_GETTER:
                    return new CustomKeyGetter(SingletonClassUtil.newCacheKeyGetter(cacheKeyRecord.getKeyGetter())).setCacheFirst(cacheFirst);
                case CacheKeyRecord.PARAM_NAMES:
                    return new PartKeyGetter(cacheKeyRecord.getParamNames()).setCacheFirst(cacheFirst);
                case CacheKeyRecord.KEY_INDEX:
                    return new IndexKeyGetter(cacheKeyRecord.getKeyIndex()).setCacheFirst(cacheFirst);
                default:
                    return new MultiKeyGetter(cacheKeyRecord).setCacheFirst(cacheFirst);
            }
        }

        /**
         * get the cache key define
         * if not, return the default params as key
         */
        private CacheKeyRecord keyDefine(Cache cache, Annotation[][] annotations) {
            CacheKeyRecord cacheKeyRecord = new CacheKeyRecord();
            int[] ints = cache.keyIndex();
            cacheKeyRecord.setKeyGetter(cache.keyGetter());
            if (CollectionUtil.isEmpty(annotations)) {
                cacheKeyRecord.setKeyIndex(ints);
                return cacheKeyRecord;
            }
            String[][] paramNames = new String[annotations.length][];
            List<Integer> indexs = Lists.newArrayList();
            List<Integer> firstIndexs = Lists.newArrayList();
            for (int i = 0; i < annotations.length; i++) {
                String[] strings = parsingCacheKey(haveCacheKey(annotations[i]));
                if (TOTAL_STRING == strings) {
                    indexs.add(i);
                    strings = NONE_STRING;
                }
                paramNames[i] = strings;

                if (haveCacheFirst(annotations[i])) {
                    firstIndexs.add(i);
                }
            }
            cacheKeyRecord.setKeyIndex(mergeIndexs(indexs, ints));
            cacheKeyRecord.setParamNames(paramNames);
            cacheKeyRecord.setCacheFirst(firstIndexs);
            return cacheKeyRecord;
        }

        private int[] mergeIndexs(List<Integer> index, int[] indexs) {
            if (CollectionUtil.isEmpty(index)) return indexs;
            if (indexs == null || indexs.length == 0) {
                return setToArray(Sets.newHashSet(index));
            }
            HashSet<Integer> integers = Sets.newHashSet(index);
            for (int i : indexs) {
                integers.add(i);
            }
            return setToArray(integers);
        }

        private int[] setToArray(Set<Integer> set) {
            if (CollectionUtil.isEmpty(set)) return new int[0];
            int[] result = new int[set.size()];
            Object[] objects = set.toArray();
            for (int i = 0; i < set.size(); i++) {
                result[i] = (Integer) objects[i];
            }
            return result;
        }

        private String[] parsingCacheKey(CacheKey cacheKey) {
            if (cacheKey == null) return NONE_STRING;
            if (CollectionUtil.isEmpty(cacheKey.name())) {
                return TOTAL_STRING;
            } else {
                return cacheKey.name();
            }
        }

        private CacheKey haveCacheKey(Annotation[] annotations) {
            if (CollectionUtil.isEmpty(annotations)) return null;
            for (Annotation annotation : annotations) {
                if (annotation.annotationType() == CacheKey.class) {
                    return (CacheKey) annotation;
                }
            }
            return null;
        }

        private boolean haveCacheFirst(Annotation[] annotations) {
            if (CollectionUtil.isEmpty(annotations)) return false;
            for (Annotation annotation : annotations) {
                if (annotation.annotationType() == CacheFirst.class) {
                    return true;
                }
            }
            return false;
        }

        private enum ParamsGetterType {
            TOTAL("total"), NONE("none"), PART("part");

            private String value;

            ParamsGetterType(String string) {
                this.value = string;
            }

            public String getValue() {
                return value;
            }
        }

        private class CacheKeyRecord {

            private Class<?>[] paramsTypes;
            private int[] keyIndex;
            private Class<? extends CacheKeyGetter> keyGetter;
            private String[][] paramNames;
            private List<Integer> cacheFirst;

            private static final int NONE_PARAMS = 0;
            private static final int KEY_GETTER = 1;
            private static final int PARAM_NAMES = 2;
            private static final int KEY_INDEX = 4;

            int haveDefine() {
                int result = 0;
                if (haveKeyGetter()) {
                    result += KEY_GETTER;
                }
                if (haveParamNames()) {
                    result += PARAM_NAMES;
                }
                if (CollectionUtil.isNotEmpty(keyIndex)) {
                    result += KEY_INDEX;
                }
                return result;
            }

            private int generateCacheFirst() {
                if (CollectionUtil.isEmpty(cacheFirst) || CollectionUtil.isEmpty(paramsTypes)) {
                    return DEFAULT_CACHE_FIRST;
                }
                for (Integer integer : cacheFirst) {
                    if (paramsTypes[integer] == boolean.class || paramsTypes[integer] == Boolean.class){
                        return integer;
                    }
                }
                return DEFAULT_CACHE_FIRST;
            }

            private boolean haveParamNames() {
                if (CollectionUtil.isEmpty(paramNames))
                    return false;
                for (String[] strings : paramNames) {
                    if (CollectionUtil.isNotEmpty(strings) && NONE_STRING != strings) {
                        return true;
                    }
                }
                return false;
            }

            boolean haveKeyGetter() {
                return keyGetter != AbstractKeyParsing.DefaultKeyGetter.class;
            }

            int[] getKeyIndex() {
                return keyIndex;
            }

            void setKeyIndex(int[] keyIndex) {
                this.keyIndex = keyIndex;
            }

            Class<? extends CacheKeyGetter> getKeyGetter() {
                return keyGetter;
            }

            void setKeyGetter(Class<? extends CacheKeyGetter> keyGetter) {
                this.keyGetter = keyGetter;
            }

            String[][] getParamNames() {
                return paramNames;
            }

            void setParamNames(String[][] paramNames) {
                this.paramNames = paramNames;
            }

            void setCacheFirst(List<Integer> first) {
                this.cacheFirst = first;
            }

            void setParamsType(Class<?>[] parameterTypes) {
                this.paramsTypes = parameterTypes;
            }
        }
    }
}
