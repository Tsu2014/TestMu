package com.tsu.chat;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import com.alibaba.android.arouter.facade.annotation.Route;
import com.tsu.annotation.BindPath;

//@Route(path = "/chat/chat")
@BindPath("chat/chat")
public class ChatActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);
    }
}