package com.example.imagelib;

import android.content.Context;
import android.widget.ImageView;

import java.lang.ref.SoftReference;

public class BitmapRequest {
    //context
    private Context context;
    //image path
    private String url;
    //image view
    private SoftReference<ImageView> imageView;
    //custom image
    private int resId;
    //callback interface
    private IRequestListener requestListener;
    //request id
    private String urlMD5;

    public BitmapRequest(Context context) {
        this.context = context;
    }

    public BitmapRequest load(String url) {
        this.url = url;
        this.urlMD5 = MD5Utils.toMD5(this.url);
        return this;
    }

    public BitmapRequest loading(int resId) {
        this.resId = resId;
        return this;
    }

    public BitmapRequest listener(IRequestListener requestListener) {
        this.requestListener = requestListener;
        return this;
    }

    public void into(ImageView imageView) {
        imageView.setTag(this.urlMD5);
        this.imageView = new SoftReference<>(imageView);
        RequestManager.getInstance().addBitmapRequest(this);
    }
}
