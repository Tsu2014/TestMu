package com.tsu.router;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import java.util.ArrayList;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import dalvik.system.DexFile;

public class SRouter {
    private static final String TAG = "SRouter";
    private static SRouter sRouter = new SRouter();

    //容器 装载全部的Activity的类对象
    private Map<String , Class<? extends Activity>> map;
    private Context context;

    private SRouter(){
        map = new HashMap<>();
    }

    public static SRouter getInstance(){
        return sRouter;
    }

    public void init(Context context){
        this.context = context;
        List<String> className = getClassName("com.tsu.util");
        for(String s : className){
            Class<?> aClass = null;
            try{
                aClass = Class.forName(s);
                if(IRouter.class.isAssignableFrom(aClass)){
                    IRouter iRouter = (IRouter)aClass.newInstance();
                    iRouter.putActivity();
                }
            }catch(Exception e){
                e.printStackTrace();
            }
        }
    }

    public void addActivity(String key , Class<? extends Activity> clazz){
        Log.d(TAG , "addActivity failed  key : "+key + " , value : "+clazz);
        if(clazz != null &&  key != null && !map.containsKey(key)){
            map.put(key , clazz);
        }else{

        }
    }

    public void jumpActivity(String key){
        jumpActivity(key , null);
    }

    public void jumpActivity(String key , Bundle bundle){
        Log.d(TAG , "map size : "+map.size());
        Set<String> keySets = map.keySet();
        Iterator<String> iterator = keySets.iterator();
        while(iterator.hasNext()){
            String key1 = iterator.next();
            Object value = map.get(key1);
            Log.d(TAG , "jumpAcitivty key : "+key1+" , value : "+value);
        }
        Class<? extends Activity> activityClass = map.get(key);
        if(activityClass == null){
            Log.d(TAG , "jumpActivity -- no activity !");
            return ;
        }

        Intent intent = new Intent(context , activityClass );
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK );
        if(bundle != null){
            intent.putExtras(bundle);
        }
        context.startActivity(intent);
    }

    private List<String> getClassName(String packageName){
        List<String> classList = new ArrayList<>();
        String path = null;
        try{
            path = context.getPackageManager().getApplicationInfo(context.getPackageName() , 0).sourceDir;

            DexFile dexfile = new DexFile(path);
            Enumeration entries = dexfile.entries();
            while(entries.hasMoreElements()){
                String name = (String)entries.nextElement();
                if(name.contains(packageName)){
                    classList.add(name);
                }
            }
        }catch(Exception e){
            e.printStackTrace();
        }

        return classList;
    }
}
