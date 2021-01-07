package com.tsu.test2;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.util.TimeUtils;

import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.TextView;

import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;

import java.util.concurrent.TimeUnit;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.Scheduler;
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
    }

    @OnClick(R.id.main_button1)
    void action1(){
        Log.d(TAG , "action1");
        Observable.interval(2,1,TimeUnit.SECONDS).doOnNext(consumer).subscribe(observer1);
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



    Consumer consumer = new Consumer<Long>() {
        @Override
        public void accept(Long o) throws Exception {
            Log.d(TAG , "times : "+o);
            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl(Consts.URL_TEST)
                    .addConverterFactory(GsonConverterFactory.create())
                    .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                    .build();

            IGetRequest request = retrofit.create(IGetRequest.class);
            Observable<Translation> observable = request.getCall();
            observable.subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(observer2);

        }
    };

    Observer<Translation> observer2 = new Observer<Translation>() {
        @Override
        public void onSubscribe(@NonNull Disposable d) {

        }

        @Override
        public void onNext(@NonNull Translation translation) {

        }

        @Override
        public void onError(@NonNull Throwable e) {
            Log.d(TAG , "request error");
        }

        @Override
        public void onComplete() {

        }
    };

    Observer<Long> observer1 = new Observer<Long>() {
        @Override
        public void onSubscribe(@NonNull Disposable d) {

        }

        @Override
        public void onNext(@NonNull Long aLong) {

        }

        @Override
        public void onError(@NonNull Throwable e) {
            Log.d(TAG , "response Error event ");
        }

        @Override
        public void onComplete() {
            Log.d(TAG , "response Complete event");
        }
    };

}