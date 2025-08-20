package com.fortytwolabs.School_Management_Project;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class CustomThreadPool {

    private static CustomThreadPool instance;
    private ExecutorService executorService;

    private CustomThreadPool(){
        executorService= Executors.newFixedThreadPool(10);
    }

    public static synchronized CustomThreadPool getInstance(){
        if(instance==null){
            instance=new CustomThreadPool();
        }
        return instance;
    }

    public void submitTask(Runnable task){
        executorService.submit(task);
    }

    public void shutdown(){
        executorService.shutdown();
    }

}
