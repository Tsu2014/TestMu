package com.tsu.testmu.activity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.alibaba.android.arouter.launcher.ARouter;
import com.tsu.annotation_bk.TSUBindView;
import com.tsu.router.SRouter;
import com.tsu.testmu.R;
import com.tsu.testmu.common.TSUButterKnife;

public class MainActivity extends AppCompatActivity {

    private final String TAG = "MainActivity";
    private static boolean isDebug = true;

    @TSUBindView(R.id.main_textView)
    TextView textView;
    private Button mainButton;

    public Button chatButton;
    private Button findButton;
    private Button homeButton;
    private Button mineButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        TSUButterKnife.bind(this);
        initViews();
        setListeners();
        init();
    }

    private void init(){
        textView.setText("122");
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
                    SRouter.getInstance().jumpActivity("/chat/main" , null);
                    break;
                case R.id.btnFind:
                    //ARouter.getInstance().build("/find/main").navigation();
                    SRouter.getInstance().jumpActivity("/find/main");
                    break;
                case R.id.btnHome:
                    //ARouter.getInstance().build("/home/main").navigation();
                    SRouter.getInstance().jumpActivity("/home/main");
                    break;
                case R.id.btnMine:
                    //ARouter.getInstance().build("/mine/main").navigation();
                    SRouter.getInstance().jumpActivity("/mine/main");
                    break;
                default:

            }
            Log.d(TAG , "onClickListenrer : "+v.getTag());
        }
    };
}