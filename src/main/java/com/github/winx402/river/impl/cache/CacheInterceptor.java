package com.github.winx402.river.impl.cache;

import com.github.winx402.river.base.AbstractExcuteRelation;

import java.lang.reflect.Method;

public class CacheInterceptor extends AbstractExcuteRelation<Method, LocalCacheTemplate> {

    protected Object implant(){
        LocalCacheTemplate bindObject = getBindObject();
        return bindObject.getResult(getParams());
    }

    public void parsingMethod(Method method) {
        LocalCacheTemplate localCacheTemplate = LocalCacheTemplate.newLocalCache(method);
        bind(method, localCacheTemplate);
    }

    protected LocalCacheTemplate getBindObject() {
        return getValue(getMethod());
    }
}
