package com.github.winx402.river.impl.cache;

import com.github.winx402.river.base.AbstractExcuteRelation;
import com.google.common.cache.LoadingCache;

import java.lang.reflect.Method;
import java.util.Collection;
import java.util.List;

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

    LocalCacheTemplate getLocalCacheTemplateCache(Method method){
        return getValue(method);
    }

    Collection<LocalCacheTemplate> getLocalCacheTemplateCaches(){
        return getValues();
    }
}
