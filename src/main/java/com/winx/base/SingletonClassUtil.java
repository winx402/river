package com.winx.base;

import com.winx.impl.cache.AbstractKeyParsing;
import com.winx.impl.cache.CacheKeyGetter;

/**
 * @author wangwenxiang
 * @create 2017-05-20.
 */
public class SingletonClassUtil {

    public static AbstractKeyParsing.WholeKeyGetter newWholeKeyGetter(){
        return ClassInstanceTool.getSingleClass(AbstractKeyParsing.WholeKeyGetter.class);
    }

    public static CacheKeyGetter newCacheKeyGetter(Class<? extends CacheKeyGetter> c){
        return ClassInstanceTool.getSingleClass(c);
    }
}
