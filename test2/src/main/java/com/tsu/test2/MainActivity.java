package com.tsu.test2;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "Test";
    @BindView(R.id.main_textView1)
    TextView textView1;

    @BindView(R.id.main_button1)
    Button button1;
    @BindView(R.id.main_button2)
    Button button2;
    @BindView(R.id.main_button3)
    Button button3;
    @BindView(R.id.main_button4)
    Button button4;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
    }

    @OnClick(R.id.main_button1)
    void action1(){
        Log.d(TAG , "action1");
    }

    @OnClick(R.id.main_button2)
    void action2(){
        Log.d(TAG , "action2");
    }

    @OnClick(R.id.main_button3)
    void action3(){
        Log.d(TAG , "action3");
    }

    @OnClick(R.id.main_button4)
    void action4(){
        Log.d(TAG , "action4");
    }
}