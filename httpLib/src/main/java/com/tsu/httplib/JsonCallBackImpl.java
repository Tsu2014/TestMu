package com.tsu.httplib;

import android.os.Handler;
import android.os.Looper;

import com.alibaba.fastjson.JSON;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class JsonCallBackImpl<M> implements CallBackListener {
    private Class<M> response;
    private IJsonListener iJsonListener;
    private Handler handler = new Handler(Looper.getMainLooper());

    public JsonCallBackImpl(Class<M> response, IJsonListener iJsonListener) {
        this.response = response;
        this.iJsonListener = iJsonListener;
    }

    @Override
    public void onSuccess(InputStream inputStream) {
        String content = getContent(inputStream);
        M m = JSON.parseObject(content, response);
        handler.post(new Runnable() {
            @Override
            public void run() {
                iJsonListener.onSuccess(m);
                //cache data for futrue
            }
        });

    }

    @Override
    public void onFaild() {
        handler.post(new Runnable() {
            @Override
            public void run() {
                iJsonListener.onFailed();
            }
        });
    }

    private String getContent(InputStream inputStream) {
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
        StringBuilder stringBuilder = new StringBuilder();
        String line = null;
        try {
            while ((line = bufferedReader.readLine()) != null) {
                stringBuilder.append(line + "/n");
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                inputStream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
            try {
                bufferedReader.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        return stringBuilder.toString().replace("/n", "");
    }
}
