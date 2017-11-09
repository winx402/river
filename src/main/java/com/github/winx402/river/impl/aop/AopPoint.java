package com.github.winx402.river.impl.aop;

import com.github.winx402.river.base.MethodPointerHandler;

/**
 * @author wangwenxiang
 * @create 2017-05-12.
 */
public abstract class AopPoint extends MethodPointerHandler.MethodPointerLimit2 {
    /**
     * before call the target method
     */
    public abstract void before() throws Exception;

    /**
     * after call the target method whether success
     */
    public abstract void after() throws Exception;

    /**
     * after call the target method when success
     */
    public abstract void afterReturing() throws Exception;

    /**
     * after call the target method when failed
     */
    public abstract void afterThrowing() throws Exception;
}
