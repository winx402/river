package com.github.winx402.river.base;

import com.google.common.base.Preconditions;
import com.google.common.collect.Maps;

import java.util.Map;

/**
 * @author wangwenxiang
 * @create 2017-05-12.
 */
public abstract class AbstractExcuteRelation<K, V> extends BaseInterceptor {
    private Map<K, V> relationMap = Maps.newHashMap();

    protected abstract V getBindObject();

    protected void newInstanceAndBind(K k, Class<? extends V> vClass) {
        V singleClass = ClassInstanceTool.getSingleClass(vClass);
        bind(k, singleClass);
    }

    protected void bind(K k, V v){
        Preconditions.checkNotNull(k);
        Preconditions.checkNotNull(v);
        relationMap.put(k, v);
    }

    protected V getValue(K k) {
        Preconditions.checkNotNull(k);
        return relationMap.get(k);
    }
}
