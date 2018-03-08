package com.github.winx402.river.Test;

import com.google.common.collect.Lists;

import java.util.Iterator;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @author didi
 */
public class ThreadTest {


    static ExecutorService executorService = Executors.newFixedThreadPool(10);

    static Runnable runnable = new Runnable() {
        public void run() {
            System.out.println(this);
            System.out.println(Thread.currentThread().getName() + ".......");
//            latch.countDown();
        }
    };

    public static void main(String[] args) {
        final List<Integer> list = Lists.newArrayList();
        for (int i = 0; i < 10 ; i ++){
            list.add(i);
        }
        Iterator<Integer> iterator = list.iterator();
        while (iterator.hasNext()){
            Integer next = iterator.next();
            if (next == 5){
                iterator.remove();
            }
        }
        System.out.println(list);
    }

    class Work<T>{
        private volatile int worked;

        private List<T> list;

        public Work(List<T> list) {
            this.list = list;
        }

        public List<T> getList() {
            List<T> objects = Lists.newArrayList();
            if (worked >= list.size()) return objects;
            synchronized (this){
                int end = worked + 5;
                objects = list.subList(worked, end);
                worked = end;
            }
            return objects;
        }
    }

}
