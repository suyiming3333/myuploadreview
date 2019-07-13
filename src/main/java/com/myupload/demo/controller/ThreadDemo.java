package com.myupload.demo.controller;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;

/**
 * @author suyiming3333@gmail.com
 * @version V1.0
 * @Title: ThreadDemo
 * @Package com.sym
 * @Description: TODO
 * @date 2019/7/11 21:50
 */
public class ThreadDemo {
    public static void main(String[] args) {
        ThreadDemo threadDemo = new ThreadDemo();
        FutureTask<Integer> futureTask = new FutureTask<Integer>((Callable<Integer>)()->{
            int i=0;
            for(;i<100;i++){
                System.out.println(Thread.currentThread().getName()+"变量的i值"+i);
//                Thread.sleep(1000);
            }
            return i++;
        });

        FutureTask<Integer> futureTask2 = new FutureTask<Integer>((Callable<Integer>)()->{
            int i=0;
            for(;i<50;i++){
                System.out.println(Thread.currentThread().getName()+"变量的i值"+i);
//                Thread.sleep(1000);
            }
            return i++;
        });

        for(int i=0;i<100;i++){
            System.out.println(Thread.currentThread().getName()+"变量的i值"+i);
            if(i == 20){
//                new Thread(futureTask).start();
                ThreadPool.getInstance().execute(futureTask);
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                ThreadPool.getInstance().execute(futureTask2);
            }
        }

        try {
            System.out.println("1子线程的返回值:"+futureTask.get());
            System.out.println(futureTask.isDone());
            System.out.println(futureTask2.isDone());
            System.out.println("2子线程的返回值:"+futureTask2.get());
            System.out.println("和"+(futureTask.get()+futureTask2.get()));
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }

//        FutureTask<Integer> futureTask1 = new FutureTask<Integer>(new Callable<Integer>() {
//            @Override
//            public Integer call() throws Exception {
//                return null;
//            }
//        });
    }
}
