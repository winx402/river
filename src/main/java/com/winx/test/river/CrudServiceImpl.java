package com.winx.test.river;

import com.winx.base.ProxyFactory;
import com.winx.exception.RateLimitTimeOutException;
import com.winx.impl.aop.Aop;
import com.winx.impl.cache.Cache;
import com.winx.impl.limit.RateLimit;
import com.winx.impl.pool.*;

import javax.annotation.Resource;
import java.util.List;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.TimeUnit;

@Resource
public class CrudServiceImpl implements com.winx.test.river.Test{

    @FixedThreadPool(threads = 10)
    public Carrier<String> aop(String param) throws InterruptedException {
//        System.out.println("aop param : " + param);
        Thread.currentThread().sleep(100);
        return AbstractCarrier.loading("result : " + param);
    }

    @Cache(maxSize = 2, timeOut = 1, timeUnit = TimeUnit.MINUTES)
    public String cache(String param){
        System.out.println("cache param : " + param);
        return param + " success";
    }

    public static void main(String[] args) throws InterruptedException {
        final CrudServiceImpl[] proxy = new CrudServiceImpl[1];
        Thread thread = new Thread(new Runnable() {
            public void run() {
                proxy[0] = ProxyFactory.getSingleProxy(CrudServiceImpl.class);
            }
        });
        thread.run();
        thread.join();
        Thread[] threads = new Thread[10];
        for (int i = 0; i < 10; i++){
            threads[i] = new Thread(new Runnable() {
                public void run() {
                    for (int i = 0; i < 1; i++){
                        try {
                            System.out.println(Thread.currentThread().getName() + ":" + proxy[0].aop("test").get());
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }, "thread-"+i);
        }
        for (int i = 0; i < 10; i++){
            threads[i].start();
        }
    }

    public void doIt() {
        System.out.println("fwefewf");
    }
}
