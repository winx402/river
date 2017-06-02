package com.winx.base;

import com.winx.exception.ImplantMethodExecuteException;
import com.winx.exception.RateLimitTimeOutException;
import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;

import java.lang.reflect.Method;

/**
 * @author wangwenxiang
 * @create 2017-05-09.
 */
public abstract class BaseInterceptor extends MethodPointerHandler.MethodPointerLimit1 implements MethodInterceptor {

    public Object intercept(Object o, Method method, Object[] objects, MethodProxy methodProxy){
        MethodPointer methodPointer = new MethodPointer(o, method, objects, methodProxy);
        addMethodPointer(methodPointer);
        try {
            return implant();
        }catch (RateLimitTimeOutException timeOut){
            throw timeOut;
        } catch (Exception e){
            throw new ImplantMethodExecuteException(e);
        }finally {
            clear();
        }
    }

    protected abstract Object implant();

    public abstract void parsingMethod(Method method);
}
