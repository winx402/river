package com.winx.util;

import java.util.Collection;
import java.util.Map;

/**
 * @author wangwenxiang
 * @create 2017-05-14.
 */
public class CollectionUtil {
    public static boolean isEmpty(Collection<?> collection) {
        return collection == null || collection.isEmpty();
    }

    public static boolean isNotEmpty(Collection<?> collection) {
        return !isEmpty(collection);
    }

    public static boolean isEmpty(Map map){
        return map == null || map.isEmpty();
    }

    public static boolean isEmpty(Object[] objects){
        return objects == null || objects.length == 0;
    }

    public static boolean isNotEmpty(Object[] objects){
        return !isEmpty(objects);
    }

    public static boolean isNotEmpty(int[] objects){
        return objects != null && objects.length != 0;
    }

    public static Object[] ArrayMerge(Object[] objects, Object[] objects1){
        if (CollectionUtil.isEmpty(objects) && CollectionUtil.isEmpty(objects1)) return new Object[0];
        if (CollectionUtil.isEmpty(objects)) return objects1;
        if (CollectionUtil.isEmpty(objects1)) return objects;
        int length = objects.length + objects1.length;
        Object[] newObj = new Object[length];
        System.arraycopy(objects, 0, newObj, 0, objects.length);
        System.arraycopy(objects1, 0, newObj, objects.length, objects1.length);
        return newObj;
    }
}
