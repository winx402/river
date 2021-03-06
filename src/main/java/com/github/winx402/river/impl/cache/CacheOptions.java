package com.github.winx402.river.impl.cache;

/**
 * @author didi
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

    private Object defaultResult;

    public CacheOptions() {}

    public CacheOptions(boolean useCache, boolean asCache) {
        this.useCache = useCache;
        this.asCache = asCache;
    }

    public Object getDefaultResult() {
        return defaultResult;
    }

    public CacheOptions setDefaultResult(Object defaultResult) {
        this.defaultResult = defaultResult;
        return this;
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
