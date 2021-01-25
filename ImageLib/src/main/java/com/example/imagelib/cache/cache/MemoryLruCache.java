package com.example.imagelib.cache.cache;

import android.util.LruCache;

import com.example.imagelib.cache.base.BaseCache;

public class MemoryLruCache<M> implements BaseCache {

    private LruCache<String , M> lruCache;

    private static volatile MemoryLruCache instance;

    private static final byte[] lock = new byte[0];

    public static MemoryLruCache getInstance(){
        if(instance == null){
            synchronized (lock){
                if(instance == null){
                    instance = new MemoryLruCache();
                }
            }
        }

        return instance;
    }

    private MemoryLruCache(){
        int maxMemorySize = (int)(Runtime.getRuntime().maxMemory()/16);
        if(maxMemorySize <=0){
            maxMemorySize = 10*1024*1024;
        }

        lruCache = new android.util.LruCache<String , M>(maxMemorySize){
            @Override
            protected int sizeOf(String key, M value) {
                return super.sizeOf(key, value);
            }
        };
    }

    @Override
    public void put(Object key, Object value) {
        if(value != null){
            lruCache.put(key.toString() , (M) value);
        }
    }

    @Override
    public M get(Object key) {
        return lruCache.get(key.toString());
    }

    @Override
    public void remove(Object key) {
        lruCache.remove(key.toString());
    }
}
