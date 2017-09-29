package com.winx.river.impl.around;

import com.winx.river.base.AbstractExcuteRelation;

import java.lang.reflect.Method;

public class AroundInterceptor extends AbstractExcuteRelation<Method, AroundPoint> {

    /**
     * implant the aop process
     */
    protected Object implant() {
        AroundPoint bindObject = getBindObject();
        return bindObject.around();
    }

    protected AroundPoint getBindObject() {
        return getValue(getMethod());
    }

    public void parsingMethod(Method method){
        Around annotation = method.getAnnotation(Around.class);
        Class<? extends AroundPoint> value = annotation.value();
        newInstanceAndBind(method, value);
    }
}
