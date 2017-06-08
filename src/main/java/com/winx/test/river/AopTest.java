package com.winx.test.river;

import com.winx.impl.aop.AopPoint;

import java.util.Arrays;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author wangwenxiang
 * @create 2017-05-15.
 */
public class AopTest extends AopPoint {

    private AtomicInteger i = new AtomicInteger(0);
    public void before() {
        System.out.println(i.incrementAndGet());
    }

    public void after() {
    }

    public void afterReturing() {
    }

    public void afterThrowing() {
    }
}
