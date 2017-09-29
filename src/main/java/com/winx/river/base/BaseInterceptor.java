package com.winx.river.base;

import com.winx.river.exception.ImplantMethodExecuteException;
import com.winx.river.exception.RateLimitTimeOutException;
import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;

import java.lang.reflect.Method;

/**
 * @author wangwenxiang
 * @create 2017-05-09.
 */
public abstract class BaseInterceptor extends MethodPointerHandler.MethodPointerLimit1 implements MethodInterceptor {

    public Object intercept(Object o, Method method, Object[] objects, MethodProxy methodProxy) {
        addMethodPointer(new MethodPointer(o, method, objects, methodProxy));
        try {
            return implant();
        } catch (RateLimitTimeOutException timeOut) {
            throw timeOut;
        } catch (Exception e) {
            throw new ImplantMethodExecuteException(e);
        } finally {
            clear();
        }
    }

    protected abstract Object implant();

    public abstract void parsingMethod(Method method);
}
