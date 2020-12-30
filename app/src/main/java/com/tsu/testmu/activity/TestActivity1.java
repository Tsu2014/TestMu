package com.tsu.testmu.activity;

import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.tsu.annotation_bk.TSUBindView;
import com.tsu.annotation_bk.TSUButterKnife;
import com.tsu.annotation_bk.TSUOnClick;
import com.tsu.testmu.R;
import com.tsu.tsu_bus.Subscribe;
import com.tsu.tsu_bus.TSUBus;
import com.tsu.tsu_bus.ThreadMode;

@Route(path = "/main/test1")
public class TestActivity1 extends AppCompatActivity {

    private static final String TAG = "TestActivity1";

    @TSUBindView(R.id.test_textView)
    public TextView textView1;
    @TSUBindView(R.id.test_button1)
    public Button button1;
    @TSUBindView(R.id.test_button2)
    public Button  button2;
    @TSUBindView(R.id.test_button3)
    public Button button3;
    @TSUBindView(R.id.test_button4)
    public Button button4;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test1);
        TSUBus.getInstance().regist(this);
        TSUButterKnife.bind(this);
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

    @TSUOnClick(R.id.test_button1)
    public void action1(){
        Log.d(TAG , "action1");
    }

    @TSUOnClick(R.id.test_button2)
    public void action2(){
        Log.d(TAG , "action2");
    }

    @TSUOnClick(R.id.test_button3)
    public void action3(){
        Log.d(TAG , "action3");
    }

    @TSUOnClick(R.id.test_button4)
    public void action4(){
        Log.d(TAG , "action4");
    }
}