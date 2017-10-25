package com.winx.river.Test;

import com.winx.river.base.ProxyFactory;
import com.winx.river.impl.cache.Cache;
import com.winx.river.impl.cache.CacheOptions;

/**
 * @author didi
 * @create 2017-09-29.
 */
public class Test {


    @Cache(maxSize = 1)
    public String get(String key, CacheOptions cacheOptions){
        System.out.println(key);
        return key;
    }

    @Cache(maxSize = 1)
    public String get1(String key, CacheOptions cacheOptions){
        System.out.println(key);
        return key;
    }

    public static void main(String[] args) {
        Test singleProxy = ProxyFactory.getSingleProxy(Test.class);
        CacheOptions cacheOptions = new CacheOptions(false, true);
        CacheOptions cacheOptions1 = new CacheOptions(true, true);
        singleProxy.get("111", cacheOptions);
        singleProxy.get("111", cacheOptions);
        singleProxy.get("111", cacheOptions);
    }

}
