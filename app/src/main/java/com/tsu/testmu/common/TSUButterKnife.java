package com.tsu.testmu.common;

import android.util.Log;

import java.lang.reflect.Method;

public class TSUButterKnife {

    private final static String TAG = "TSUButterKnife";

    public static void bind(Object activity){
        String name = activity.getClass().getName();
        String binderName = name+"$$ViewBinder";
        Log.d(TAG , "binderName : "+binderName);
        try {
            Class<?> aClass = Class.forName(binderName);
            Object binder = aClass.newInstance();
            //1 fanshe
            //Method bind = aClass.getDeclaredMethod("bind" , activity.getClass());
            //bind.invoke(binder , activity);

            //2
            ITSUButterKnifer itsuButterKnifer = (ITSUButterKnifer) aClass.newInstance();
            itsuButterKnifer.bind(activity);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
