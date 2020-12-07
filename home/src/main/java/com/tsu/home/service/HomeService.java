package com.tsu.home.service;

import android.content.Context;

import com.base.commonlib.service.HomeExportService;

public class HomeService implements HomeExportService {
    @Override
    public String sayHello(String string) {
        return "Hello "+string;
    }

    @Override
    public void init(Context context) {

    }
}
