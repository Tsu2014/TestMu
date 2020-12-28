package com.tsu.tsu_bus;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TSUBus {

    private static TSUBus tsuBus = new TSUBus();
    private Map<Object , List<MethodManager>> map;

    private TSUBus(){
        map = new HashMap<>();
    }

    public static TSUBus getInstance(){
        return tsuBus;
    }

    public void regist(Object obeject){
        List<MethodManager> methods = map.get(obeject);
        if(methods == null){
            //search regist method
            methods = findMethod(obeject);
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



}
