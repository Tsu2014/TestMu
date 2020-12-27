package com.tsu.httplib;

import android.util.Log;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.DelayQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.RejectedExecutionHandler;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * manager request task
 */
public class ThreadManager {

    private static final String TAG = "ThreadManager";
    private static final int MAX_FAILED_TIME = 3;
    private static ThreadManager threadManager = new ThreadManager();
    private LinkedBlockingQueue<Runnable> mQueue = new LinkedBlockingQueue<>();
    private ThreadPoolExecutor threadPoolExecutor;
    private DelayQueue<HttpTask> failedQueue = new DelayQueue<HttpTask>();

    private ThreadManager(){
        threadPoolExecutor = new ThreadPoolExecutor(3, 10, 15, TimeUnit.SECONDS, new ArrayBlockingQueue<Runnable>(4), new RejectedExecutionHandler() {
            @Override
            public void rejectedExecution(Runnable runnable, ThreadPoolExecutor threadPoolExecutor) {
                mQueue.add(runnable);
            }
        });
        threadPoolExecutor.execute(runnable);
        threadPoolExecutor.execute(failedRunnable);
    }

    public static ThreadManager getInstance(){
        return threadManager;
    }

    public void addTask(Runnable runnable){
        if(runnable != null){
            mQueue.add(runnable);
        }
    }

    public void addFailedTask(HttpTask httpTask){
        if(httpTask == null){
            return ;
        }
        failedQueue.offer(httpTask);
    }

    /**
     * core Thread
     */
    public Runnable runnable = new Runnable() {
        @Override
        public void run() {
            while(true){
                try {
                    Runnable httpTask = mQueue.take();
                    threadPoolExecutor.execute(httpTask);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    };

    /**
     * failed Thread
     */
    public Runnable failedRunnable = new Runnable() {
        @Override
        public void run() {
            while(true){
                try{
                    HttpTask task = failedQueue.take();
                    if(task.getFailedCount() < MAX_FAILED_TIME){
                        task.setFailedCount(task.getFailedCount()+1);
                        threadPoolExecutor.execute(task);
                        Log.e(TAG , "retry !");
                    }else{
                        //final failed
                        task.getiHttpRequest().getCallBackListener().onFaild();
                    }
                }catch(Exception e){
                    e.printStackTrace();
                }
            }
        }
    };
}
