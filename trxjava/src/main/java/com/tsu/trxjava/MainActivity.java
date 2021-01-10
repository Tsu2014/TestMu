package com.tsu.trxjava;

import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;

import java.util.Iterator;
import java.util.Map;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

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

        Observable.create(new Observable<String>() {
            @Override
            public void subscribe(Observer<String> observer) {
                for (int i=0 ;i<10;i++){
                    observer.onNext("value : "+i);
                }
                observer.onComplete();
            }
        }).subscribe(new Observer<String>() {
            @Override
            public void onNext(String s) {
                Log.d(TAG , "观察者收到消息是 ： "+s);
            }

            @Override
            public void onComplete() {
                Log.d(TAG , "all over !!!");
            }

            @Override
            public void onError(Throwable e) {

            }
        });
    }

    @OnClick(R.id.main_button1)
    void action1(){
        Log.d(TAG , "action1");

    }

    @OnClick(R.id.main_button2)
    void action2(){

    }

    private void testSystem(){

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