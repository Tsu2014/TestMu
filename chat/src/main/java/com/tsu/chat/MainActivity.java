package com.tsu.chat;

import android.os.Bundle;
import android.text.TextUtils;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.alibaba.android.arouter.facade.annotation.Autowired;
import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.tsu.annotation.BindPath;

//@Route(path = "/chat/main")
@BindPath("/chat/main")
public class MainActivity extends AppCompatActivity {

    private TextView textTV;

    @Autowired
    public String key3;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat_main);

        ARouter.getInstance().inject(this);
        Toast.makeText(this , "receive key : "+key3 , Toast.LENGTH_SHORT).show();
        initViews();
        setListeners();
        init();
    }

    private void initViews(){
        textTV = findViewById(R.id.tvCenter);
    }

    private void setListeners(){

    }

    private void init(){
        if(!TextUtils.isEmpty(key3)){
            textTV.setText(key3);
        }
    }
}