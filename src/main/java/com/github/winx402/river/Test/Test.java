package com.github.winx402.river.Test;

import com.github.winx402.river.base.ProxyFactory;
import com.github.winx402.river.impl.cache.Cache;
import com.github.winx402.river.impl.cache.CacheHandler;
import com.github.winx402.river.impl.cache.CacheOptions;

import java.lang.reflect.Method;

/**
 * @author didi
 */
public class Test {


    @Cache(maxSize = 10)
    protected String get(String s){
        System.out.println(s);
        return null;
    }

    @Cache(maxSize = 1)
    public String get1(String key, CacheOptions cacheOptions){
        System.out.println(key);
        return key;
    }

    public static void main(String[] args) throws NoSuchMethodException {
        Test singleProxy = ProxyFactory.getSingleProxy(Test.class);
        System.out.println(singleProxy.get("1"));
        System.out.println(singleProxy.get1("1", null));
        Method method = Test.class.getDeclaredMethod("get", String.class);
        CacheHandler.clear(method);
        System.out.println(singleProxy.get("1"));
        System.out.println(singleProxy.get1("1", null));
    }

}
