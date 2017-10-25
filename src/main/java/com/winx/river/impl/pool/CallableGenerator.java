package com.winx.river.impl.pool;

import com.winx.river.base.MethodPointerHandler;
import com.winx.river.exception.UnknownResultTypeException;

import java.util.concurrent.Callable;

/**
 * @author wangwenxiang
 * @create 2017-05-27.
 */
public class CallableGenerator extends MethodPointerHandler.MethodPointerLimit5 {

    static Callable generateCallable() {
        ReturnType returnType = getReturnType();
        if (returnType == ReturnType.VOID) {
            return new RunnableAdapter(new RunnableCarrier(), null);
        }
        if (returnType == ReturnType.UNKNOW)
            throw new UnknownResultTypeException("unknown result type for method : " + methodName());
        return new CallableCarrier();
    }

    private static class RunnableCarrier extends MethodPointerHandler.MethodPointerLimit5 implements Runnable {
        public void run() {
            doInvoke();
        }
    }

    private static class CallableCarrier extends MethodPointerHandler.MethodPointerLimit5 implements Callable {

        public Object call() throws Exception {
            return doInvoke();
        }
    }

    /**
     * A callable that runs given task and returns given result
     */
    static final class RunnableAdapter<T> implements Callable<T> {
        final Runnable task;
        final T result;

        RunnableAdapter(Runnable task, T result) {
            this.task = task;
            this.result = result;
        }

        public T call() {
            task.run();
            return result;
        }
    }
}
