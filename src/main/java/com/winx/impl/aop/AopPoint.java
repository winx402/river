package com.winx.impl.aop;

import com.winx.base.MethodPointerHandler;

/**
 * @author wangwenxiang
 * @create 2017-05-12.
 */
public abstract class AopPoint extends MethodPointerHandler.MethodPointerLimit2 {
    /**
     * before call the target method
     */
    public abstract void before();

    /**
     * after call the target method whether success
     */
    public abstract void after();

    /**
     * after call the target method when success
     */
    public abstract void afterReturing();

    /**
     * after call the target method when failed
     */
    public abstract void afterThrowing();
}
