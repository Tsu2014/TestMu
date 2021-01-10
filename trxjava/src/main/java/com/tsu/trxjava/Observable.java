package com.tsu.trxjava;

public abstract class Observable<T> {

    protected Observable(){

    }

    //create operation
    public static <T> Observable<T> create(Observable<T> observable){
        return observable;
    }

    public abstract void subscribe(Observer<T> observer);

}
