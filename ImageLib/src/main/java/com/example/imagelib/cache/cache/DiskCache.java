package com.example.imagelib.cache.cache;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Environment;

import com.example.imagelib.cache.base.BaseCache;
import com.example.imagelib.cache.util.DiskLruCache;
import com.example.imagelib.cache.util.Util;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.InputStream;
import java.io.OutputStream;

public class DiskCache<K, V> implements BaseCache {

    private DiskLruCache diskLruCache;

    private static volatile DiskCache instance;

    private String imageCachePath = "Datas";

    private static final byte[] lock = new byte[0];

    private int MB = 1024*1024;

    private int maxDiskSize = 50*MB;

    private DiskCache(Context context){
        File cacheFile = getImageCacheFile(context, imageCachePath);
        if(!cacheFile.exists()){
            cacheFile.mkdirs();
        }

        try{
            diskLruCache = DiskLruCache.open(cacheFile, getAppVersion(context) , 1 ,  maxDiskSize);
        }catch(Exception e){
            e.printStackTrace();
        }

    }

    public static DiskCache getInstance(Context context){
        if(instance == null){
            synchronized (lock){
                if(instance == null){
                    instance = new DiskCache(context);
                }
            }
        }

        return instance;
    }

    private int getAppVersion(Context context){
        PackageManager packageManager = context.getPackageManager();
        try{
            PackageInfo info = packageManager.getPackageInfo(context.getPackageName() , 0);
            return info.versionCode;
        }catch(Exception e){
            e.printStackTrace();
        }

        return 1;
    }

    private File getImageCacheFile(Context context , String imageCachePath){
        String path;
        if(Environment.MEDIA_MOUNTED.equals(Environment.getExternalStorageState())){
            path = context.getExternalCacheDir().getPath();
        }else{
            path = context.getCacheDir().getPath();
        }

        return new File(path+File.separator + imageCachePath);
    }

    private boolean presetBitmap2Disk(OutputStream outputStream , Object value){
        BufferedOutputStream bufferedOutputStream = null;
        try{
            bufferedOutputStream = new BufferedOutputStream(outputStream);
            if(value.getClass().isAssignableFrom(Bitmap.class)){
                Bitmap bitmap = (Bitmap) value;
                bitmap.compress(Bitmap.CompressFormat.JPEG , 100 , bufferedOutputStream);
            }

            bufferedOutputStream.flush();
        }catch(Exception e){
            e.printStackTrace();
        }finally {
            Util.closeQuietly(bufferedOutputStream);
        }

        return true;
    }

    @Override
    public Bitmap get(Object key) {
        InputStream stream = null;
        try{
            DiskLruCache.Snapshot snapshot = diskLruCache.get(key.toString());
            if(snapshot != null){
                stream = snapshot.getInputStream(0);
                return BitmapFactory.decodeStream(stream);
            }
        }catch(Exception e){
            e.printStackTrace();
        }

        return null;
    }

    @Override
    public void put(java.lang.Object key, java.lang.Object value) {
        DiskLruCache.Editor editor;
        OutputStream outputStream = null;
        try{
            editor = diskLruCache.edit(key.toString());
            outputStream = editor.newOutputStream(0);
            if(presetBitmap2Disk(outputStream , value)){
                editor.commit();
            }else{
                editor.abort();
            }
        }catch(Exception e){
            e.printStackTrace();
        }finally {
            Util.closeQuietly(outputStream);
        }
    }

    @Override
    public void remove(java.lang.Object key) {

    }
}
