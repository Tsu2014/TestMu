package com.tsu.router;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import java.util.Map;

public class SRouter {
    private static final String TAG = "SRouter";
    private static SRouter sRouter = new SRouter();

    //容器 装载全部的Activity的类对象
    private Map<String , Class<? extends Activity>> map;
    private Context context;

    private SRouter(){

    }

    public static SRouter getInstance(){
        return sRouter;
    }

    public void init(Context context){
        this.context = context;
    }

    public void addActivity(String key , Class<? extends Activity> clazz){
        if(clazz != null &&  key != null && !map.containsKey(key)){
            map.put(key , clazz);
        }
    }

    public void jumpActivity(String key , Bundle bundle){
        Class<? extends Activity> activityClass = map.get(key);
        if(activityClass == null){
            Log.d(TAG , "jumpActivity -- no activity !");
            return ;
        }

        Intent intent = new Intent(context , activityClass );
        if(bundle != null){
            intent.putExtras(bundle);
        }
        context.startActivity(intent);
    }
}
