package com.winx.river.base;

import com.google.common.collect.Maps;
import com.winx.river.exception.ImplantMethodExecuteException;
import com.winx.river.impl.pool.ReturnType;
import net.sf.cglib.proxy.MethodProxy;

import java.lang.reflect.Method;
import java.util.Map;

/**
 * @author wangwenxiang
 * @create 2017-05-15.
 */
public class MethodPointerHandler {

    private static InheritableThreadLocal<MethodPointer> pointerThreadLocal = new InheritableThreadLocal<MethodPointer>();

    private static final Map<Method, ReturnType> RESULT_TYPE_MAP = Maps.newHashMap();

    public static class MethodPointerLimit1 {
        protected void addMethodPointer(MethodPointer methodPointer) {
            pointerThreadLocal.set(methodPointer);
        }

        protected Object getInstance() {
            return pointerThreadLocal.get().getInstance();
        }

        protected Method getMethod() {
            return pointerThreadLocal.get().getMethod();
        }

        protected MethodProxy getMethodProxy() {
            return pointerThreadLocal.get().getMethodProxy();
        }

        protected void putResultType(Method method, ReturnType type) {
            RESULT_TYPE_MAP.put(method, type);
        }

        protected ReturnType getResultType(Method method) {
            return RESULT_TYPE_MAP.get(method);
        }

        protected Object[] getParams() {
            return pointerThreadLocal.get().getObjects();
        }

        protected void setParams(Object[] objects) {
            pointerThreadLocal.get().setObjects(objects);
        }

        protected Object doInvoke() {
            try {
                return pointerThreadLocal.get().doInvoke();
            } catch (Throwable throwable) {
                throw new ImplantMethodExecuteException(throwable);
            }
        }

        protected Object getResult() {
            return pointerThreadLocal.get().getResult();
        }

        protected void setResult(Object result) {
            pointerThreadLocal.get().setResult(result);
        }

        protected void clear() {
            pointerThreadLocal.remove();
        }
    }

    public static class MethodPointerLimit2 {

        /**
         * 获取目标方法Method对象
         */
        protected Method getMethod() {
            return pointerThreadLocal.get().getMethod();
        }

        /**
         * 获取入参
         */
        protected Object[] getParams() {
            return pointerThreadLocal.get().getObjects();
        }

        /**
         * 修改入参
         */
        protected void setParams(Object[] objects) {
            pointerThreadLocal.get().setObjects(objects);
        }

        /**
         * 获取返回结果
         * 在before中调用这个方法将返回null
         */
        protected Object getResult() {
            return pointerThreadLocal.get().getResult();
        }

        /**
         * 修改返回结果
         * 在before中设置的返回结果将被正真的结果覆盖
         */
        protected void setResult(Object result) {
            pointerThreadLocal.get().setResult(result);
        }
    }

    public static class MethodPointerLimit3 {

        protected Method getMethod() {
            return pointerThreadLocal.get().getMethod();
        }

        protected Object[] getParams() {
            return pointerThreadLocal.get().getObjects();
        }

        protected void setParams(Object[] objects) {
            pointerThreadLocal.get().setObjects(objects);
        }

        protected Object getResult() {
            return pointerThreadLocal.get().getResult();
        }

        protected void setResult(Object result) {
            pointerThreadLocal.get().setResult(result);
        }

        protected Object doInvoke() {
            try {
                return pointerThreadLocal.get().doInvoke();
            } catch (Throwable throwable) {
                throw new ImplantMethodExecuteException(throwable);
            }
        }
    }

    public static class MethodPointerLimit4 {
        protected static Object doInvoke() {
            try {
                return pointerThreadLocal.get().doInvoke();
            } catch (Throwable throwable) {
                throw new ImplantMethodExecuteException(throwable);
            }
        }

        protected static void setResult(Object result) {
            pointerThreadLocal.get().setResult(result);
        }
    }

    public static class MethodPointerLimit5 {
        protected static Object doInvoke() {
            try {
                return pointerThreadLocal.get().doInvoke();
            } catch (Throwable throwable) {
                throw new ImplantMethodExecuteException(throwable);
            }
        }

        protected static String methodName() {
            return pointerThreadLocal.get().getMethod().getName();
        }

        protected static ReturnType getReturnType() {
            return pointerThreadLocal.get().getReturnType();
        }
    }

    public static class MethodPointerLimit6 {

        protected static ReturnType getReturnType() {
            return pointerThreadLocal.get().getReturnType();
        }
    }

    public static class MethodPointerLimit7 {

        protected Method getMethod() {
            return pointerThreadLocal.get().getMethod();
        }

        protected Object getResult() {
            return pointerThreadLocal.get().getResult();
        }

    }
}
