package com.tsu.tsu_bus;

import java.lang.reflect.Method;

public class MethodManager {

    private Method method;

    private Class<?> type;

    private ThreadMode threadMode;

    private String key;

    public MethodManager(Method method, Class<?> type, ThreadMode threadMode, String key) {
        this.method = method;
        this.type = type;
        this.threadMode = threadMode;
        this.key = key;
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

    public ThreadMode getThreadMode() {
        return threadMode;
    }

    public void setThreadMode(ThreadMode threadMode) {
        this.threadMode = threadMode;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }
}
