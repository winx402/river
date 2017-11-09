package com.github.winx402.river.base;

import com.github.winx402.river.impl.cache.AbstractKeyParsing;
import com.github.winx402.river.impl.cache.CacheKeyGetter;

/**
 * @author wangwenxiang
 */
public class SingletonClassUtil {

    public static AbstractKeyParsing.DefaultKeyGetter newWholeKeyGetter(){
        return ClassInstanceTool.getSingleClass(AbstractKeyParsing.DefaultKeyGetter.class);
    }

    public static CacheKeyGetter newCacheKeyGetter(Class<? extends CacheKeyGetter> c){
        return ClassInstanceTool.getSingleClass(c);
    }
}
