package com.winx.test.river;

import com.winx.base.ProxyFactory;
import com.winx.exception.RateLimitTimeOutException;
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

@Resource
public class CrudServiceImpl implements com.winx.test.river.Test{

    @ThreadPool(corePoolSize = 5, maximumPoolSize = 10, keepAliveTime = 10)
    public Carrier<Integer> create() throws InterruptedException {
        System.out.println(Thread.currentThread().getName() + "create......");
        Thread.sleep(2000);
        return AbstractCarrier.loading(12);
    }

    public List<String> retrieve(String condition) {
        System.out.println("retrieve......");
        return null;
    }

    public void update(Long id, String content) {
        System.out.println("update......");
    }

    public boolean delete(Long id) {
        System.out.println("delete......");
        return false;
    }

    public static void main(String[] args) throws ExecutionException, InterruptedException {
        final CrudServiceImpl proxy = ProxyFactory.getSingleProxy(CrudServiceImpl.class);
        proxy.doIt();
//        long l = System.currentTimeMillis();
//        Thread[] threads = new Thread[10];
//        for (int i = 0; i < 10; i++){
//            threads[i] = new Thread(new Runnable() {
//                public void run() {
//                    for (int i = 0; i < 1; i++){
//                        try {
//                            try {
//                                System.out.println(Thread.currentThread().getName() + ":" + proxy.create().get());
//                            } catch (InterruptedException e) {
//                                e.printStackTrace();
//                            }
//                        }catch (RateLimitTimeOutException e){
//                            System.out.println(Thread.currentThread().getName() + "超时");
//                        }
//
//                    }
//                }
//            }, "thread-"+i);
//        }
//        for (int i = 0; i < 10; i++){
//            threads[i].start();
//        }
//        for (int i = 0; i < 10; i++){
//            threads[i].join();
//        }
//        System.out.println("cost:" +(System.currentTimeMillis() - l));
    }

    public void doIt() {
        System.out.println("fwefewf");
    }
}
