package com.example.imagelib;

import android.content.Context;

public class ImageUtil {
    public static BitmapRequest with(Context context){
        return new BitmapRequest(context);
    }
}
