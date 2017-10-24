package com.winx.river.util;

import com.google.common.base.Objects;
import com.google.common.base.Preconditions;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;

/**
 * @author didi
 * @create 2017-10-24.
 */
public class BeanUtil {
    public static void copyProperties(Object source, Object target){
        Preconditions.checkNotNull(source, "Source must not be null");
        Preconditions.checkNotNull(target, "Target must not be null");
        Field[] sourceFields = source.getClass().getDeclaredFields();
        Field[] targetFields = target.getClass().getDeclaredFields();
        if (CollectionUtil.isEmpty(sourceFields) || CollectionUtil.isEmpty(targetFields)) return;
        for (Field sourceField : sourceFields){
            for (Field targetField : targetFields){
                if (Objects.equal(sourceField.getName(), targetField.getName())){
                    if (!Modifier.isPublic(sourceField.getDeclaringClass().getModifiers())){
                        sourceField.setAccessible(true);
                    }
                    if (!Modifier.isPublic(targetField.getDeclaringClass().getModifiers())){
                        targetField.setAccessible(true);
                    }
                    try {
                        targetField.set(target, sourceField.get(source));
                    } catch (IllegalAccessException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }
}
