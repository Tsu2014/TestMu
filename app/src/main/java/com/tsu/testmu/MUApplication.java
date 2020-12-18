package com.tsu.testmu;

import android.app.Application;

import com.alibaba.android.arouter.launcher.ARouter;
import com.tsu.CommonLibTest;
import com.tsu.router.SRouter;
import com.tsu.testmu.activity.MainActivity;

public class MUApplication extends Application {

    private static final String TAG = "MUApplication";
    private static boolean isDebug = true;
    @Override
    public void onCreate() {
        super.onCreate();
        initARouterSDK();
        SRouter.getInstance().init(this);
    }

    private void initARouterSDK(){
        if(isDebug()){
            ARouter.openLog();
            ARouter.openDebug();
        }
        ARouter.init(this);
    }

    public boolean isDebug() {
        return isDebug;
    }
}
