package com.winx.river.spring;

import com.google.common.base.Objects;
import com.google.common.base.Preconditions;
import com.winx.river.util.CollectionUtil;

import java.lang.reflect.Field;

/**
 * @author didi
 * @create 2017-10-24.
 */
class BeanUtil {
    static void copyProperties(Object source, Object target) {
        Preconditions.checkNotNull(source, "Source must not be null");
        Preconditions.checkNotNull(target, "Target must not be null");
        Class<?> superclass = target.getClass().getSuperclass();
        Field[] sourceFields = source.getClass().getDeclaredFields();
        Field[] targetFields = superclass.getDeclaredFields();
        if (CollectionUtil.isEmpty(sourceFields) || CollectionUtil.isEmpty(targetFields)) return;
        for (Field sourceField : sourceFields) {
            for (Field targetField : targetFields) {
                if (Objects.equal(sourceField.getName(), targetField.getName())) {
                    sourceField.setAccessible(true);
                    targetField.setAccessible(true);
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
