package com.tsu.httplib;

import java.io.BufferedOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import javax.net.ssl.HttpsURLConnection;

public class HttpRequest implements IHttpRequest{

    private String url;
    private byte[] bytes;
    private CallBackListener callBackListener;
    private HttpURLConnection httpURLConnection;
    private HttpsURLConnection httpsURLConnection;

    @Override
    public void setUrl(String url) {
        this.url = url;
    }

    @Override
    public void setData(byte[] bytes) {
        this.bytes = bytes;
    }

    @Override
    public void setListener(CallBackListener callBackListener) {
        this.callBackListener = callBackListener;
    }

    @Override
    public void excute() {
        URL url = null;
        try {
            url = new URL(this.url);
            httpURLConnection = (HttpURLConnection) url.openConnection();
            httpURLConnection.setConnectTimeout(6*1000);
            httpURLConnection.setUseCaches(false);
            httpURLConnection.setInstanceFollowRedirects(true);
            httpURLConnection.setReadTimeout(3000);
            httpURLConnection.setDoInput(true);
            httpURLConnection.setDoOutput(true);
            httpURLConnection.setRequestMethod("POST");
            httpURLConnection.setRequestProperty("Content-Type","application/json;charset=UTF-8");
            httpURLConnection.connect();

            OutputStream out = httpURLConnection.getOutputStream();
            BufferedOutputStream bos = new BufferedOutputStream(out);
            bos.write(bytes);
            bos.flush();
            out.close();
            bos.close();

            if(httpURLConnection.getResponseCode() == HttpURLConnection.HTTP_OK){
                InputStream in = httpURLConnection.getInputStream();
                callBackListener.onSuccess(in);
            }else{
                //retry
                throw new RuntimeException("request failed !");
            }
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
