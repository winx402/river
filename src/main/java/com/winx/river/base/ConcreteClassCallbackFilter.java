package com.winx.river.base;

import com.winx.river.util.CollectionUtil;
import net.sf.cglib.proxy.CallbackFilter;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;

public class ConcreteClassCallbackFilter implements CallbackFilter {

    /**
     * 默认不走代理
     */
    static final int DEFAULT_METHOD_INDEX = 0;

    public int accept(Method method) {
        Annotation[] annotations = method.getAnnotations();
        return cons(annotations);
    }

    private int cons(Annotation[] annotations) {
        if (CollectionUtil.isEmpty(annotations)) {
            return DEFAULT_METHOD_INDEX;
        }
        for (Annotation annotation1 : annotations) {
            Class<? extends Annotation> annotationType = annotation1.annotationType();
            if (annotationType == null || !FunctionDistribute.getAnnotationSet().contains(annotationType)){
                continue;
            }
            return FunctionDistribute.indexOfAnnotation(annotationType);
        }
        return DEFAULT_METHOD_INDEX;
    }
}