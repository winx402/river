package com.winx.base;

import com.sun.org.apache.regexp.internal.RE;
import com.winx.impl.pool.ReturnType;
import net.sf.cglib.proxy.MethodProxy;

import java.lang.reflect.Method;

/**
 * @author wangwenxiang
 * @create 2017-05-13.
 */
public class MethodPointer {
    private Object instance;

    private Method method;

    private Object[] objects;

    private MethodProxy methodProxy;

    private Object result;

    private ReturnType returnType;

    public MethodPointer(Object instance, Method method, Object[] objects, MethodProxy methodProxy) {
        this.instance = instance;
        this.method = method;
        this.objects = objects;
        this.methodProxy = methodProxy;
    }

    public Object getInstance() {
        return instance;
    }

    public void setInstance(Object instance) {
        this.instance = instance;
    }

    public Method getMethod() {
        return method;
    }

    public void setMethod(Method method) {
        this.method = method;
    }

    public Object[] getObjects() {
        return objects;
    }

    public void setObjects(Object[] objects) {
        this.objects = objects;
    }

    public MethodProxy getMethodProxy() {
        return methodProxy;
    }

    public void setMethodProxy(MethodProxy methodProxy) {
        this.methodProxy = methodProxy;
    }

    public Object doInvoke() throws Throwable {
        return methodProxy.invokeSuper(instance, objects);
    }

    public Object getResult() {
        return result;
    }

    public void setResult(Object result) {
        this.result = result;
    }

    public ReturnType getReturnType() {
        return returnType;
    }

    public void setReturnType(ReturnType returnType) {
        this.returnType = returnType;
    }
}
