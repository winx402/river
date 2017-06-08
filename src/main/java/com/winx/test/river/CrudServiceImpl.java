package com.winx.test.river;

import com.winx.base.ProxyFactory;
import com.winx.exception.RateLimitTimeOutException;
import com.winx.impl.aop.Aop;
import com.winx.impl.cache.Cache;
import com.winx.impl.limit.RateLimit;
import com.winx.impl.pool.AbstractCarrier;
import com.winx.impl.pool.Carrier;
import com.winx.impl.pool.SingleThreadExecutor;
import com.winx.impl.pool.ThreadPool;

import javax.annotation.Resource;
import java.util.List;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.TimeUnit;

@Resource
public class CrudServiceImpl implements com.winx.test.river.Test{

    @Aop(AopTest.class)
    public String aop(String param) throws InterruptedException {
//        System.out.println("aop param : " + param);
        Thread.currentThread().sleep(100);
        return param + " success";
    }

    @Cache(maxSize = 2, timeOut = 1, timeUnit = TimeUnit.MINUTES)
    public String cache(String param){
        System.out.println("cache param : " + param);
        return param + " success";
    }

    public static void main(String[] args){
        final CrudServiceImpl proxy = ProxyFactory.getSingleProxy(CrudServiceImpl.class);
        for (int i = 0; i < 10; i ++){
            System.out.println(proxy.cache("cache key"));
        }
//        long l = System.currentTimeMillis();
//        Thread[] threads = new Thread[10];
//        for (int i = 0; i < 10; i++){
//            threads[i] = new Thread(new Runnable() {
//                public void run() {
//                    for (int i = 0; i < 1; i++){
//                        try {
//                            System.out.println(Thread.currentThread().getName() + ":" + proxy.aop("test"));
//                        } catch (InterruptedException e) {
//                            e.printStackTrace();
//                        }
//                    }
//                }
//            }, "thread-"+i);
//        }
//        for (int i = 0; i < 10; i++){
//            threads[i].start();
//        }
//        System.out.println("cost:" +(System.currentTimeMillis() - l));
    }

    public void doIt() {
        System.out.println("fwefewf");
    }
}
