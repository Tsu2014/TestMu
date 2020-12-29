package com.tsu.tsu_bus;

import android.util.Log;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class TSUBus {

    private final static String TAG = "TSUBus";
    private static TSUBus tsuBus = new TSUBus();
    private Map<Object , List<MethodManager>> map;

    private TSUBus(){
        map = new HashMap<>();
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
            MethodManager methodManager = new MethodManager(declaredMethod , parameterTypes[0]);
            methods.add(methodManager);
        }

        return methods;
    }

    /**
     * broastcase
     * @param setter
     */
    public void post(Object setter){
        Log.d(TAG , "post : "+setter);
        Set<Object> keySet = map.keySet();
        for(Object object : keySet){
            List<MethodManager> methodManagers = map.get(object);
            for(MethodManager methodManager :methodManagers){
                Class<?> type = methodManager.getType();
                Method method = methodManager.getMethod();
                if(type.isAssignableFrom(setter.getClass())){
                    try {
                        method.invoke(object , setter);
                    } catch (IllegalAccessException e) {
                        e.printStackTrace();
                    } catch (InvocationTargetException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }



}
