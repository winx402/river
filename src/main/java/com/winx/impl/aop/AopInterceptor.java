package com.winx.impl.aop;

import com.winx.base.AbstractExcuteRelation;
import com.winx.exception.ImplantMethodExecuteException;

import java.lang.reflect.Method;

public class AopInterceptor extends AbstractExcuteRelation<Method, AopPoint> {

    /**
     * implant the aop process
     */
    protected Object implant() {
        try {
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
            return result;
        }catch (Exception e){
            throw new ImplantMethodExecuteException(e);
        }
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
