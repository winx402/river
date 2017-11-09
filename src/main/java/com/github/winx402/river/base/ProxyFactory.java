package com.github.winx402.river.base;

import com.google.common.base.Preconditions;
import com.google.common.collect.Maps;
import com.github.winx402.river.exception.GenerateProxyException;
import net.sf.cglib.proxy.Enhancer;

import java.util.Map;

/**
 * @author wangwenxiang
 */
public class ProxyFactory {

    private static final Map<Class, Object> singleMap = Maps.newConcurrentMap();

    public static Object getObjectProxy(Object o){
        if (o == null) return null;
        Class<?> aClass = o.getClass();
        Object o1 = singleMap.get(aClass);
        if (o1 != null) return o1;
        synchronized (ProxyFactory.class) {
            if (FunctionDistribute.distributeAndBind(aClass)) {
                return newProxy(aClass);
            }
            return o;
        }
    }

    public static <T> T getSingleProxy(Class<T> tClass) {
        Preconditions.checkNotNull(tClass);
        Object o = singleMap.get(tClass);
        if (o != null) return (T) o;
        T t = newProxyOrObject(tClass);
        if (t != null) singleMap.put(tClass, t);
        return t;
    }


    static <T> T newProxyOrObject(Class<T> tClass) {
        synchronized (ProxyFactory.class) {
            if (FunctionDistribute.distributeAndBind(tClass)) {
                return (T) newProxy(tClass);
            }
            try {
                return tClass.newInstance();
            } catch (Exception e) {
                throw new GenerateProxyException(e);
            }
        }
    }

    private static  Object newProxy(Class<?> tClass){
        Enhancer enhancer = new Enhancer();
        enhancer.setSuperclass(tClass);
        enhancer.setCallbacks(FunctionDistribute.getBaseInterceptorSet());
        enhancer.setCallbackFilter(new ConcreteClassCallbackFilter());
        Object o = enhancer.create();
        singleMap.put(tClass, o);
        return o;
    }
}
