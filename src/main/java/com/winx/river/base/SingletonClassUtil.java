package com.winx.river.base;

import com.winx.river.impl.cache.AbstractKeyParsing;
import com.winx.river.impl.cache.CacheKeyGetter;

/**
 * @author wangwenxiang
 * @create 2017-05-20.
 */
public class SingletonClassUtil {

    public static AbstractKeyParsing.DefaultKeyGetter newWholeKeyGetter(){
        return ClassInstanceTool.getSingleClass(AbstractKeyParsing.DefaultKeyGetter.class);
    }

    public static CacheKeyGetter newCacheKeyGetter(Class<? extends CacheKeyGetter> c){
        return ClassInstanceTool.getSingleClass(c);
    }
}
