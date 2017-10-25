package com.winx.river.Test;

import com.winx.river.base.ProxyFactory;
import com.winx.river.impl.cache.Cache;
import com.winx.river.impl.cache.CacheFirst;

/**
 * @author didi
 * @create 2017-09-29.
 */
public class Test {


    @Cache
    public String get(String key,@CacheFirst boolean f){
        System.out.println(key);
        return key;
    }

    public static void main(String[] args) {
        Test singleProxy = ProxyFactory.getSingleProxy(Test.class);
        singleProxy.get("111", false);
        singleProxy.get("111", false);
        singleProxy.get("333", false);
        singleProxy.get("111", true);
        singleProxy.get("111", false);
    }

}
