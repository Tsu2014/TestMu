package com.example.imagelib;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Handler;
import android.os.Looper;
import android.widget.ImageView;

import androidx.annotation.Nullable;

import com.example.imagelib.cache.cache.DoubleLruCache;

import java.io.FileOutputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.concurrent.LinkedBlockingQueue;

/**
 * get object by queue , then request image that thread
 */
public class BitmapDispatcher extends Thread {

    private LinkedBlockingQueue<BitmapRequest> mQueue;

    //
    private Handler handler = new Handler(Looper.getMainLooper());

    private DoubleLruCache doubleLruCache = new DoubleLruCache(null);

    public BitmapDispatcher(LinkedBlockingQueue<BitmapRequest> mQueue) {
        this.mQueue = mQueue;
    }


    @Override
    public void run() {
        while(true){
            //loop get request , then request
            try {
                BitmapRequest bitmapRequest = mQueue.take();
                //show default image
                showLoadingImage(bitmapRequest);
                Bitmap bitmap = findBitmap(bitmapRequest);
                showImageView(bitmapRequest , bitmap);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

    }

    private void showImageView(BitmapRequest bitmapRequest , Bitmap bitmap){
        final ImageView imageView  = bitmapRequest.getImageView();
        if(bitmap != null && imageView!= null && imageView.getTag().equals(bitmapRequest.getUrlMD5())){
            handler.post(new Runnable() {
                @Override
                public void run() {
                    imageView.setImageBitmap(bitmap);
                }
            });
        }

    }

    private Bitmap findBitmap(BitmapRequest bitmapRequest) {
        Bitmap bitmap = null;
        //first check memory

        //second check disk


        //
        bitmap = (Bitmap)doubleLruCache.get(bitmapRequest.getUrlMD5());


        if(bitmap != null){
            return bitmap;
        }
        bitmap = downloadImage(bitmapRequest.getUrl());
        if(bitmap != null) {
            doubleLruCache.put(bitmapRequest.getUrlMD5(), bitmap);
        }

        return bitmap;
    }

    private Bitmap downloadImage(String uri) {
        FileOutputStream fos = null;
        InputStream is = null;
        Bitmap bitmap = null;
        try {
            URL url = new URL(uri);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            is = conn.getInputStream();
            bitmap = BitmapFactory.decodeStream(is);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (is != null) {
                    is.close();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            try {
                if (fos != null) {
                    fos.close();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        return bitmap;
    }

    private void showLoadingImage(BitmapRequest bitmapRequest) {
        //change to main thread
        final int resID = bitmapRequest.getResId();
        final ImageView imageView = bitmapRequest.getImageView();
        if (resID > 0 && imageView != null) {
            handler.post(new Runnable() {
                @Override
                public void run() {
                    imageView.setImageResource(resID);
                }
            });
        }
    }
}
