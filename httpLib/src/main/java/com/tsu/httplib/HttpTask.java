package com.tsu.httplib;

import com.alibaba.fastjson.JSON;

import java.io.UnsupportedEncodingException;
import java.util.concurrent.Delayed;
import java.util.concurrent.TimeUnit;

/**
 * request task
 */
public class HttpTask<T> implements Runnable , Delayed {

    private IHttpRequest iHttpRequest;

    private int failedCount;

    private long delayTime;

    public long getDelayTime() {
        return delayTime;
    }

    public void setDelayTime(long delayTime) {
        this.delayTime = delayTime+System.currentTimeMillis();
    }

    public int getFailedCount() {
        return failedCount;
    }

    public void setFailedCount(int failedCount) {
        this.failedCount = failedCount;
    }

    public HttpTask(IHttpRequest iHttpRequest , String url , T requestData , CallBackListener callBackListener){
        this.iHttpRequest = iHttpRequest;
        this.iHttpRequest.setUrl(url);
        this.iHttpRequest.setListener(callBackListener);
        if(requestData != null){
            String jsonStr = JSON.toJSONString(requestData);
            try {
                this.iHttpRequest.setData(jsonStr.getBytes("UTF-8"));
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
        }else{

        }

    }

    @Override
    public void run() {
        try{
            this.iHttpRequest.excute();
        }catch(Exception e){
            e.printStackTrace();
            //retry
            ThreadManager.getInstance().addFailedTask(this);
        }

    }

    @Override
    public long getDelay(TimeUnit timeUnit) {
        return timeUnit.convert(getDelayTime() - System.currentTimeMillis() , TimeUnit.MILLISECONDS);
    }

    @Override
    public int compareTo(Delayed delayed) {
        return 0;
    }

    public IHttpRequest getiHttpRequest(){
        return this.iHttpRequest;
    }

}
