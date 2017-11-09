package com.github.winx402.river.impl.pool;

import com.github.winx402.river.base.MethodPointerHandler;
import com.github.winx402.river.exception.GetFutureObjectException;

import java.util.concurrent.Future;

/**
 * @author wangwenxiang
 */
public abstract class AbstractCarrier<T> extends MethodPointerHandler.MethodPointerLimit6 implements Carrier<T> {

    protected T t;

    public static <K> Carrier<K> loading(K k) {
        return new DirectCarrier<K>(k);
    }

    static Object generateReturnCarrier(Future future) {
        ReturnType returnType = getReturnType();
        if (returnType == ReturnType.VOID) return null;
        if (returnType == ReturnType.OTHER) {
            try {
                return future.get();
            } catch (Exception e) {
                throw new GetFutureObjectException(e);
            }
        }
        return new FutureCarrier(future);
    }

    private static class DirectCarrier<T> extends AbstractCarrier<T> {

        private DirectCarrier(T t) {
            this.t = t;
        }

        public T get() {
            return t;
        }
    }

    private static class FutureCarrier<T> extends AbstractCarrier<T> {

        private Future<Carrier<T>> future;

        private boolean gain = false;

        FutureCarrier(Future<Carrier<T>> carrierFuture) {
            this.future = carrierFuture;
        }

        public T get() {
            if (gain) return t;
            try {
                Carrier<T> tCarrier = future.get();
                gain = true;
                if (tCarrier == null) return null;
                t = tCarrier.get();
                return t;
            } catch (Exception e) {
                throw new GetFutureObjectException(e);
            }
        }
    }

}
