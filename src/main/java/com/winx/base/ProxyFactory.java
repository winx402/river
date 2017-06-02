package com.winx.base;

import com.google.common.base.Preconditions;
import com.google.common.collect.Maps;
import com.winx.exception.GenerateProxyException;
import net.sf.cglib.proxy.Enhancer;

import java.util.Map;

/**
 * @author wangwenxiang
 * @create 2017-05-10.
 */
public class ProxyFactory {

    private static final Map<Class, Object> singleMap = Maps.newConcurrentMap();

    public static <T> T getSingleProxy(Class<T> tClass) {
        Preconditions.checkNotNull(tClass);
        Object o = singleMap.get(tClass);
        if (o != null) return (T) o;
        T t = newProxy(tClass);
        if (t != null) singleMap.put(tClass, t);
        return t;
    }


    public static <T> T newProxy(Class<T> tClass) {
        synchronized (ProxyFactory.class) {
            if (FunctionDistribute.distributeAndBind(tClass)) {
                Enhancer enhancer = new Enhancer();
                enhancer.setSuperclass(tClass);
                enhancer.setCallbacks(FunctionDistribute.getBaseInterceptorSet());
                enhancer.setCallbackFilter(new ConcreteClassCallbackFilter());
                Object o1 = enhancer.create();
                singleMap.put(tClass, o1);
                return (T) o1;
            }
            try {
                return tClass.newInstance();
            } catch (Exception e) {
                throw new GenerateProxyException(e);
            }
        }
    }
}
