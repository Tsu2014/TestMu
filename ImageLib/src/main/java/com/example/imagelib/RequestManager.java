package com.example.imagelib;

import java.util.concurrent.LinkedBlockingQueue;

public class RequestManager {

    private final static String TAG = "RequestManager";
    private static RequestManager requestManager = new RequestManager();

    private LinkedBlockingQueue<BitmapRequest> mQueue;

    private BitmapDispatcher [] bitmapDispatchers;
    private int limit;

    private RequestManager(){
        mQueue = new LinkedBlockingQueue<>();
        createAndOpenThread();
    }

    private void createAndOpenThread(){
        int count = Runtime.getRuntime().availableProcessors();
        bitmapDispatchers = new BitmapDispatcher[count];
        for(int i =0;i<count;i++){
            BitmapDispatcher bitmapDispatcher = new BitmapDispatcher();
            bitmapDispatcher.start();
            bitmapDispatchers[i] = bitmapDispatcher;

        }
    }

    public static RequestManager getInstance(){
        return requestManager;
    }

    public void addBitmapRequest(BitmapRequest bitmapRequest){
        if(!mQueue.contains(bitmapRequest)){
            mQueue.add(bitmapRequest);
        }
    }

}
