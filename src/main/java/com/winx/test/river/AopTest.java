package com.winx.test.river;

import com.winx.impl.aop.AopPoint;

import java.util.Arrays;

/**
 * @author wangwenxiang
 * @create 2017-05-15.
 */
public class AopTest extends AopPoint {

    private AopTest(){}

    public void before() {
        System.out.println("params:" + Arrays.toString(getParams()));
    }

    public void after() {
        System.out.println("result:" + getResult());
    }

    public void afterReturing() {
    }

    public void afterThrowing() {
    }
}
