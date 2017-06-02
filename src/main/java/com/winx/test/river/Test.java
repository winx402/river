package com.winx.test.river;

import com.winx.impl.around.Around;

/**
 * @author wangwenxiang
 * @create 2017-05-11.
 */
public interface Test {
    @Around(AopTest1.class)
    void doIt();
}
