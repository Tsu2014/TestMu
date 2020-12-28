package com.tsu.tsu_bus;

import java.lang.reflect.Method;

public class MethodManager {

    private Method method;

    private Class<?> type;

    public MethodManager(Method method , Class<?> type){
        this.method = method;
        this.type = type;
    }

    public Method getMethod() {
        return method;
    }

    public void setMethod(Method method) {
        this.method = method;
    }

    public Class<?> getType() {
        return type;
    }

    public void setType(Class<?> type) {
        this.type = type;
    }
}
