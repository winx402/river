package com.github.winx402.river.impl.aop;

import com.github.winx402.river.base.MethodPointerHandler;

/**
 * @author wangwenxiang
 */
public abstract class AopPoint extends MethodPointerHandler.MethodPointerLimit2 {
    /**
     * before call the target method
     * @throws Exception Exception
     */
    public abstract void before() throws Exception;

    /**
     * after call the target method whether success
     * @throws Exception Exception
     */
    public abstract void after() throws Exception;

    /**
     * after call the target method when success
     * @throws Exception Exception
     */
    public abstract void afterReturing() throws Exception;

    /**
     * after call the target method when failed
     * @throws Exception Exception
     */
    public abstract void afterThrowing() throws Exception;
}
