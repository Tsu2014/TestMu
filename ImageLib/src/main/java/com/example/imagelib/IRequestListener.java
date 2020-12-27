package com.example.imagelib;

import android.graphics.Bitmap;

public interface IRequestListener {

    void onSuccess(Bitmap bitmap);
    void onFailed();

}
