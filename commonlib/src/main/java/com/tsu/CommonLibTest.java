package com.tsu;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import com.tsu.annotation.AnnotationTest;

public class CommonLibTest {

    private static String TAG = "CommonLibTest";

    public static void main(String [] args){
        AnnotationTest test = new AnnotationTest();
    }

    public static void test(){
        Log.d(TAG , TAG);
    }

    public static void test(Context context , String text){
        Toast.makeText(context , text , Toast.LENGTH_SHORT).show();
    }
}
