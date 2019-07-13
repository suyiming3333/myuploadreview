package com.myupload.demo.controller;

import com.google.common.util.concurrent.ThreadFactoryBuilder;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * @author suyiming3333@gmail.com
 * @version V1.0
 * @Title: asd
 * @Package com.sym
 * @Description: TODO
 * @date 2019/7/11 23:30
 */
public class ThreadPool {

    private static ExecutorService instance = null;

    private ThreadPool(){

    }

    public static ExecutorService getInstance(){
        if(instance == null){
            synchronized (ExecutorService.class){
                if (instance == null){
                    instance = new ThreadPoolExecutor(
                            5,
                            200,
                            0L,
                            TimeUnit.MILLISECONDS,
                            new LinkedBlockingDeque<Runnable>(1024),
                            new ThreadFactoryBuilder().setNameFormat("demo-pool-%d").build(),
                            new ThreadPoolExecutor.AbortPolicy());
                }
            }
        }

        return instance;

    }
}
