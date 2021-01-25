package com.example.imagelib.cache.cache;

import android.content.Context;

import com.example.imagelib.cache.base.BaseCache;

public class DoubleLruCache<T, M> implements BaseCache {

    private MemoryLruCache lruCache;
    private DiskCache diskCache;

    public DoubleLruCache(Context context){
        diskCache = DiskCache.getInstance(context);
        lruCache = MemoryLruCache.getInstance();
    }

    @Override
    public void put(Object key, Object value) {
        lruCache.put(key , value);
        diskCache.put(key , value);
    }

    @Override
    public M get(Object key) {
        M value = (M)lruCache.get(key);
        if(value == null){
            value = (M)diskCache.get(key);
            lruCache.put(key, value);
        }

        return value;
    }

    @Override
    public void remove(Object key) {
        lruCache.remove(key);
        diskCache.remove(key);
    }
}
