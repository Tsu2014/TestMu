package com.tsu.testmu.activity;

import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.tsu.testmu.R;
import com.tsu.tsu_bus.Subscribe;
import com.tsu.tsu_bus.TSUBus;
import com.tsu.tsu_bus.ThreadMode;

@Route(path = "/main/test1")
public class TestActivity1 extends AppCompatActivity {

    private static final String TAG = "TestActivity1";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test1);
        TSUBus.getInstance().regist(this);
    }

    @Subscribe(threadMode = ThreadMode.MAIN , key = "tsu2")
    public void getMessage(String text){
        Toast.makeText(this , "Test : "+text , Toast.LENGTH_SHORT).show();
        Log.d(TAG , "Test1 : "+text);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Log.d(TAG , "onBackPressed");
        this.finish();
    }

    @Override
    protected void onStop() {
        super.onStop();
        TSUBus.getInstance().unRegist(this);
    }
}