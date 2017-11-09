package com.github.winx402.river.Test;

import com.github.winx402.river.base.ProxyFactory;
import com.github.winx402.river.impl.cache.Cache;
import com.github.winx402.river.impl.cache.CacheOptions;

/**
 * @author didi
 */
public class Test {


    @Cache(maxSize = 1)
    protected String get(String key, CacheOptions cacheOptions){
        System.out.println(key);
        return null;
    }

    @Cache(maxSize = 1)
    public String get1(String key, CacheOptions cacheOptions){
        System.out.println(key);
        return key;
    }

    public static void main(String[] args) {
        Test singleProxy = ProxyFactory.getSingleProxy(Test.class);
        System.out.println(singleProxy.get("1111", new CacheOptions(true, true).setDefaultResult("hhhhh")));
    }

}
