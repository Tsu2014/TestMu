package com.tsu.annotation_bk;

public class TSUButterKnife {

    private final static String TAG = "TSUButterKnife";

    public static void bind(Object activity){
        String name = activity.getClass().getName();
        String binderName = name+"$$ViewBinder";
        try {
            Class<?> aClass = Class.forName(binderName);
            Object binder = aClass.newInstance();
            //1 fanshe
            //Method bind = aClass.getDeclaredMethod("bind" , activity.getClass());
            //bind.invoke(binder , activity);

            //2
            ITSUButterKnifer itsuButterKnifer = (ITSUButterKnifer) binder;
            itsuButterKnifer.bind(activity);

        } catch (Exception e) {
            e.printStackTrace();
        }

        setListener(activity);
    }

    public static void setListener(Object activity){
        String name = activity.getClass().getName();
        String binderName = name+"$$OnClick";
        try {
            Class<?> aClass = Class.forName(binderName);
            Object binder = aClass.newInstance();
            if(binder == null){
                return ;
            }
            //1 fanshe
            //Method bind = aClass.getDeclaredMethod("bind" , activity.getClass());
            //bind.invoke(binder , activity);

            //2
            ITSUButterKnifer1 itsuButterKnifer = (ITSUButterKnifer1) binder;
            itsuButterKnifer.setListener(activity);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
