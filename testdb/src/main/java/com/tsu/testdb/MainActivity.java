package com.tsu.testdb;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.tsu.testdb.lib.BaseDao;
import com.tsu.testdb.lib.BaseDaoFactory;
import com.tsu.testdb.lib.User;

public class MainActivity extends AppCompatActivity {

    private final static String TAG = "MainActivity";
    private TextView textView1;
    private Button button1;
    private Button button2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        textView1 = findViewById(R.id.main_textview1);
        button1 = findViewById(R.id.main_button1);
        button2 = findViewById(R.id.main_button2);

        button1.setOnClickListener(onClickListener);
        button2.setOnClickListener(onClickListener);

        BaseDaoFactory.getInstance().init(BaseDaoFactory.PATH);
    }

    private View.OnClickListener onClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.main_button1:
                    action1();
                    break;
                case R.id.main_button2:
                    action2();
                    break;
            }
        }
    };

    private void action1() {
        BaseDao<User> baseDao = BaseDaoFactory.getInstance().getBaseDao(User.class);
        baseDao.insert(new User("tsu2021" , "123456"));

    }

    private void action2() {

    }
}