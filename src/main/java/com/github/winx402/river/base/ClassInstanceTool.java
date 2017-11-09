package com.github.winx402.river.base;

import com.github.winx402.river.exception.ClassInstanceException;
import com.google.common.base.Preconditions;
import com.google.common.collect.Maps;

import java.lang.reflect.Constructor;
import java.util.Map;

/**
 * @author wangwenxiang
 */
public class ClassInstanceTool {
    private static Map<Class, Object> singletonMap = Maps.newHashMap();

    private static Class[] cl = {};

    static <T> T getSingleClass(Class<? extends T> c){
        Preconditions.checkNotNull(c,"class key is not allow null");
        Object object = singletonMap.get(c);
        if (object == null){
            synchronized(c) {
                if (singletonMap.get(c) == null) {
                    try {
                        object = newIns(c);
                        singletonMap.put(c, object);
                    } catch (Exception e) {
                        throw new ClassInstanceException("get singleton class error", e);
                    }
                }
            }
        }
        return (T) singletonMap.get(c);
    }

    public static <T> T newInstance(Class<? extends T> tClass){
        try {
            return newIns(tClass);
        } catch (Exception e) {
            throw new ClassInstanceException("new instance for "+tClass.getName()+" error", e);
        }
    }

    private static <T> T newIns(Class<? extends T> tClass) throws Exception{
        T t;
        try {
            t = tClass.newInstance();
        }catch (Exception e){
            Constructor<? extends T> declaredConstructor = tClass.getDeclaredConstructor(cl);
            declaredConstructor.setAccessible(true);
            t = declaredConstructor.newInstance();
            declaredConstructor.setAccessible(false);
        }
        return t;
    }
}