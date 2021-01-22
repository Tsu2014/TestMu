package com.tsu.testmu.activity;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.base.commonlib.utils.ImageUtils;
import com.example.imagelib.IRequestListener;
import com.example.imagelib.ImageUtil;
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
    public Button button2;
    @TSUBindView(R.id.test_button3)
    public Button button3;
    @TSUBindView(R.id.test_button4)
    public Button button4;

    public ImageView imageView;
    LinearLayout scrooll_line;
    String[] urlArray = new String[]{
            "https://www.baidu.com/img/flexible/logo/pc/result.png",
            "https://dgss0.bdstatic.com/5bVWsj_p_tVS5dKfpU_Y_D3/res/r/image/2017-09-27/297f5edb1e984613083a2d3cc0c5bb36.png",
            "https://mat1.gtimg.com/pingjs/ext2020/qqindex2018/dist/img/qq_logo_2x.png"
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test1);
        imageView = findViewById(R.id.test_imageView1);
        scrooll_line = findViewById(R.id.test_scroll_line);
        TSUBus.getInstance().regist(this);
        TSUButterKnife.bind(this);

    }

    public void loadImage(View view) {
        ImageUtil.with(this).load("").loading(R.drawable.ic_launcher_background).into(imageView);
    }

    public void single(View view) {
        ImageView imageView = new ImageView(this);
        imageView.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
        scrooll_line.addView(imageView);
        ImageUtil.with(this).load(urlArray[0]).loading(R.drawable.ic_launcher_background).into(imageView);
    }

    public void more(View view) {
        for (int i = 0; i < 3; i++) {
            ImageView imageView = new ImageView(this);
            imageView.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT , ViewGroup.LayoutParams.WRAP_CONTENT));
            imageView.setScaleType(ImageView.ScaleType.CENTER_INSIDE);
            scrooll_line.addView(imageView);
            String url = urlArray[i];
            ImageUtil.with(this).load(url).loading(R.drawable.ic_launcher_background).listener(new IRequestListener() {
                @Override
                public void onSuccess(Bitmap bitmap) {
                    Log.d(TAG , "onSuccess !");
                }

                @Override
                public void onFailed() {
                    Log.d(TAG , "onFailed");
                }
            }).into(imageView);
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN, key = "tsu2")
    public void getMessage(String text) {
        Toast.makeText(this, "Test : " + text, Toast.LENGTH_SHORT).show();
        Log.d(TAG, "Test1 : " + text);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Log.d(TAG, "onBackPressed");
        this.finish();
    }

    @Override
    protected void onStop() {
        super.onStop();
        TSUBus.getInstance().unRegist(this);
    }

    @TSUOnClick(R.id.test_button1)
    public void action1() {
        Log.d(TAG, "action1");
    }

    @TSUOnClick(R.id.test_button2)
    public void action2() {
        Log.d(TAG, "action2");
    }

    @TSUOnClick(R.id.test_button3)
    public void action3() {
        Log.d(TAG, "action3");
    }

    @TSUOnClick(R.id.test_button4)
    public void action4() {
        Log.d(TAG, "action4");
    }
}