package com.tsu.tsu_bus;

import android.os.Handler;
import android.os.Looper;
import android.util.Log;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class TSUBus {

    private final static String TAG = "TSUBus";
    private static TSUBus tsuBus = new TSUBus();
    private Map<Object , List<MethodManager>> map;
    private Handler handler = new Handler(Looper.getMainLooper());

    //thread service object
    private ExecutorService executorService;
    private TSUBus(){
        map = new HashMap<>();
        handler = new Handler(Looper.getMainLooper());
        executorService = Executors.newCachedThreadPool();
    }

    public static TSUBus getInstance(){
        return tsuBus;
    }

    public void regist(Object object){
        List<MethodManager> methods = map.get(object);
        if(methods == null){
            //search regist method
            methods = findMethod(object);
            map.put(object , methods);
        }
    }

    public void unRegist(Object object){
        if(map.get(object) != null){
            map.remove(object);
        }
    }

    private List<MethodManager> findMethod(Object object){
        List<MethodManager> methods = new ArrayList<>();
        Class<?> aClass = object.getClass();
        Method[] declaredMethods = aClass.getDeclaredMethods();
        for(Method declaredMethod : declaredMethods){
            Subscribe annotation = declaredMethod.getAnnotation(Subscribe.class);
            if(annotation == null){
                continue;
            }

            Class<?> [] parameterTypes = declaredMethod.getParameterTypes();
            //subscrib method only has one parameter
            if(parameterTypes == null || parameterTypes.length != 1){
                continue;
            }
            //get threadMode by annotation
            ThreadMode threadMode = annotation.threadMode();
            String key = annotation.key();
            MethodManager methodManager = new MethodManager(declaredMethod , parameterTypes[0] , threadMode , key);
            methods.add(methodManager);
        }

        return methods;
    }

    /**
     * broastcase
     * @param setter
     */
    public void post(Object setter , String key){
        Log.d(TAG , "post : "+setter);
        Set<Object> keySet = map.keySet();
        for(Object object : keySet){
            List<MethodManager> methodManagers = map.get(object);
            for(MethodManager methodManager :methodManagers){
                Class<?> type = methodManager.getType();
                Method method = methodManager.getMethod();
                ThreadMode threadMode = methodManager.getThreadMode();
                String annoKey = methodManager.getKey();
                if(!annoKey.equals(key)){
                    continue;
                }
                if(type.isAssignableFrom(setter.getClass())){
                    switch(threadMode){
                        case POSTING:
                            invoke(object , setter , method);
                            break;
                        case MAIN:
                            if(Looper.myLooper() == Looper.getMainLooper()){
                                invoke(object , setter , method);
                            }else{
                                handler.post(new Runnable() {
                                    @Override
                                    public void run() {
                                        invoke(object , setter , method);
                                    }
                                });
                            }
                            break;
                        case BACKGROUND:
                            if(Looper.myLooper() == Looper.getMainLooper()){
                                executorService.execute(new Runnable() {
                                    @Override
                                    public void run() {
                                        invoke(object , setter , method);
                                    }
                                });
                            }else{
                                invoke(object , setter , method);
                            }
                            break;
                    }

                }
            }
        }
    }

    public void invoke(Object object , Object setter , Method method){
        try {
            method.invoke(object , setter);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
    }

}
