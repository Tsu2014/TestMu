package com.example.imagelib.cache.base;

public interface BaseCache<T , M> {

    void put(T key , M value);

    M get(T key);

    void remove(T key);

}
