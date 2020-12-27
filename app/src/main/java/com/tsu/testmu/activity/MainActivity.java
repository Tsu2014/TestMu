package com.tsu.testmu.activity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.alibaba.android.arouter.launcher.ARouter;
import com.tsu.annotation_bk.TSUBindView;
import com.tsu.annotation_bk.TSUButterKnife;
import com.tsu.annotation_bk.TSUOnClick;
import com.tsu.httplib.IJsonListener;
import com.tsu.httplib.TSUHttp;
import com.tsu.router.SRouter;
import com.tsu.testmu.R;

public class MainActivity extends AppCompatActivity {

    private final String TAG = "MainActivity";
    private static boolean isDebug = true;

    @TSUBindView(R.id.main_textView)
    TextView textView;
    @TSUBindView(R.id.btnMain)
    Button mainButton;
    @TSUBindView(R.id.btnChat)
    Button chatButton;
    @TSUBindView(R.id.btnFind)
    Button findButton;
    @TSUBindView(R.id.btnHome)
    Button homeButton;
    @TSUBindView(R.id.btnMine)
    Button mineButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        TSUButterKnife.bind(this);
        //setListeners();
        init();
    }

    private void init(){
        textView.setText("123");
    }

    @TSUOnClick(R.id.btnChat)
    public void doChatAction(){
        SRouter.getInstance().jumpActivity("/chat/main" , null);
    }

    @TSUOnClick(R.id.btnFind)
    public void doFindAction(){
        SRouter.getInstance().jumpActivity("/find/main");
    }

    @TSUOnClick(R.id.btnHome)
    public void doHomeAction(){
        SRouter.getInstance().jumpActivity("/home/main");
    }

    @TSUOnClick(R.id.btnMine)
    public void doMineAction(){
        SRouter.getInstance().jumpActivity("/mine/main");
    }

    @TSUOnClick(R.id.btnMain)
    public void doMainAction(){
        ARouter.getInstance().build("/main/test1").navigation();
    }

    public void sendMessage(View view){
        TSUHttp.sendMessage("", null, null, new IJsonListener() {
            @Override
            public void onSuccess(Object L) {

            }

            @Override
            public void onFailed() {

            }
        });
    }

}