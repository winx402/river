package com.winx.base;

import net.sf.cglib.proxy.CallbackFilter;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;

public class ConcreteClassCallbackFilter implements CallbackFilter {

    public int accept(Method method) {
        Annotation[] annotations = method.getAnnotations();
        return cons(annotations);
    }

    private int cons(Annotation[] annotations) {
        if (annotations == null || annotations.length == 0) {
            return 0;
        }
        for (Annotation annotation1 : annotations) {
            if (FunctionDistribute.getAnnotationSet().contains(annotation1.annotationType())) {
                return FunctionDistribute.indexOfAnnotation(annotation1.annotationType());
            }
        }
        return 0;
    }
}