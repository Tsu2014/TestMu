package com.tsu.testmu.activity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.alibaba.android.arouter.launcher.ARouter;
import com.tsu.router.SRouter;
import com.tsu.testmu.R;

public class MainActivity extends AppCompatActivity {

    private final String TAG = "MainActivity";
    private static boolean isDebug = true;
    private Button mainButton;
    private Button chatButton;
    private Button findButton;
    private Button homeButton;
    private Button mineButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initViews();
        setListeners();
        init();
    }

    private void init(){

    }

    private void initViews(){
        mainButton = findViewById(R.id.btnMain);
        chatButton = findViewById(R.id.btnChat);
        findButton = findViewById(R.id.btnFind);
        homeButton = findViewById(R.id.btnHome);
        mineButton = findViewById(R.id.btnMine);
    }

    private void setListeners(){
        mainButton.setOnClickListener(onClickListener);
        chatButton.setOnClickListener(onClickListener);
        findButton.setOnClickListener(onClickListener);
        homeButton.setOnClickListener(onClickListener);
        mineButton.setOnClickListener(onClickListener);
    }

    private View.OnClickListener onClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch(v.getId()){
                case R.id.btnMain:
                    Log.d(TAG , "button1");
                    ARouter.getInstance().build("/main/test1").navigation();
                    break;
                case R.id.btnChat:
                    //ARouter.getInstance().build("/chat/main").withString("key3","hello").navigation();
                    SRouter.getInstance().jumpActivity("/chat/chat" , null);
                    break;
                case R.id.btnFind:
                    ARouter.getInstance().build("/find/main").navigation();
                    break;
                case R.id.btnHome:
                    ARouter.getInstance().build("/home/main").navigation();
                    break;
                case R.id.btnMine:
                    ARouter.getInstance().build("/mine/main").navigation();
                    break;
                default:

            }
            Log.d(TAG , "onClickListenrer : "+v.getTag());
        }
    };
}