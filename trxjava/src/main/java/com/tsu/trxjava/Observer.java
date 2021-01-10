package com.tsu.trxjava;

public interface Observer<T> {
    void onNext(T t);
    void onComplete();
    void onError(Throwable e);
}
