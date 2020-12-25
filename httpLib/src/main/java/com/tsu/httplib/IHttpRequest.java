package com.tsu.httplib;

public interface IHttpRequest {
    void setUrl(String url);
    void setData(byte[] bytes);
    void setListener(CallBackListener callBackListener);
    void excute();
}
