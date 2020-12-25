package com.tsu.httplib;

public interface IJsonListener<T> {
    //success
    void onSuccess(T L);
    //failed
    void onFailed();
}
