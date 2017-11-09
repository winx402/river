package com.github.winx402.river.impl.pool;

import com.github.winx402.river.base.AbstractExcuteRelation;
import com.github.winx402.river.base.MethodPointer;

import java.lang.reflect.Method;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;

/**
 * @author wangwenxiang
 */
public class ThreadPoolInterceptor extends AbstractExcuteRelation<Method, ExecutorService> {

    protected ExecutorService getBindObject() {
        return getValue(getMethod());
    }

    protected Object implant() {
        ExecutorService executorService = getBindObject();
        Callable callable = CallableGenerator.generateCallable();
        Future submit = executorService.submit(callable);
        return AbstractCarrier.generateReturnCarrier(submit);
    }

    public void parsingMethod(Method method) {
        ExecutorService executorService = ThreadPoolManager.buildExecutor(method);
        putResultType(method, ReturnType.parsingReturnType(method));
        bind(method, executorService);
    }

    @Override
    protected void addMethodPointer(MethodPointer methodPointer) {
        //set return type
        methodPointer.setReturnType(getResultType(methodPointer.getMethod()));
        super.addMethodPointer(methodPointer);
    }
}
