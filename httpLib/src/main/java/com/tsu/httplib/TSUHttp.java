package com.tsu.httplib;

public class TSUHttp {

    public static<T,M> void sendMessage(String url , T requestData , Class<M> response , IJsonListener iJsonListener){
        IHttpRequest iHttpRequest = new HttpRequest();
        CallBackListener callBackListener = new JsonCallBackImpl(response , iJsonListener);

        HttpTask httpTask = new HttpTask(iHttpRequest , url , requestData , callBackListener);

        ThreadManager.getInstance().addTask(httpTask);
    }
}
