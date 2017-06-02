package com.winx.impl.aop;

import com.winx.base.AbstractExcuteRelation;

import java.lang.reflect.Method;

public class AopInterceptor extends AbstractExcuteRelation<Method, AopPoint> {

    /**
     * implant the aop process
     */
    protected Object implant() {
        AopPoint bindObject = getBindObject();
        Object result = null;
        bindObject.before();
        try {
            result = doInvoke();
            setResult(result);
            bindObject.afterReturing();
        } catch (Throwable e) {
            bindObject.afterThrowing();
        } finally {
            bindObject.after();
        }
        getInstance();
        return result;
    }

    protected AopPoint getBindObject() {
        return getValue(getMethod());
    }

    public void parsingMethod(Method method) {
        Aop annotation = method.getAnnotation(Aop.class);
        Class<? extends AopPoint> value = annotation.value();
        newInstanceAndBind(method, value);
    }
}
