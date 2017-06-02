package com.winx.test.river;

import com.winx.impl.cache.CacheKeyGetter;

/**
 * @author wangwenxiang
 * @create 2017-05-21.
 */
public class CustomerKeyGetter implements CacheKeyGetter {
    public Object[] getKey(Object[] params) {
        return new Object[]{params[0]};
    }
}