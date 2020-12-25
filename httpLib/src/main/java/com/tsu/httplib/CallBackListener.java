package com.tsu.httplib;

import java.io.InputStream;

public interface CallBackListener<T> {
    void onSuccess(InputStream inputStream);
    void onFaild();
}
