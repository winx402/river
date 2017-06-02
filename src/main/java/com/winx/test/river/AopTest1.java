package com.winx.test.river;

import com.winx.impl.around.AroundPoint;

/**
 * @author wangwenxiang
 * @create 2017-05-15.
 */
public class AopTest1 extends AroundPoint {
    public Object around() {
        System.out.println("hhhhhh");
        return doInvoke();
    }
}
