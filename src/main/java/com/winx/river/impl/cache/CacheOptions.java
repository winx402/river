package com.winx.river.impl.cache;

/**
 * @author didi
 * @create 2017-10-25.
 */
public class CacheOptions {
    /**
     * use history cache as method result
     * true: get result data if this cache exists
     * false: not use cache whether it exists or not
     */
    private boolean useCache = true;

    /**
     * set this method result as cache if not from cache
     * true: set as
     * false: not set as
     */
    private boolean asCache = true;

    public CacheOptions() {}

    public CacheOptions(boolean useCache, boolean asCache) {
        this.useCache = useCache;
        this.asCache = asCache;
    }

    public void useCache(){
        useCache = true;
    }

    public void notUseCache(){
        useCache = false;
    }

    public void asCache(){
        asCache = true;
    }

    public void notAsCache(){
        asCache = false;
    }

    public boolean isUseCache(){
        return useCache;
    }

    public boolean isAsCache(){
        return asCache;
    }
}
