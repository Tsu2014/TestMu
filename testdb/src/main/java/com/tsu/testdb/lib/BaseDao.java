package com.tsu.testdb.lib;

import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class BaseDao<T> implements IBaseDao<T>{

    private Class<T> clazz;
    private SQLiteDatabase sqLiteDatabase;
    private String tableName;

    public BaseDao(Class<T> clazz , SQLiteDatabase sqLiteDatabase){
        //create table
        this.clazz = clazz;
        this.sqLiteDatabase = sqLiteDatabase;
        cacheMap = new HashMap<String , Field>();
        //get Table's name
        tableName = this.clazz.getAnnotation(DBTable.class).value();

        //create table sql
        String createTableStr = createTableStr();
        this.sqLiteDatabase.execSQL(createTableStr);
        initCacheMap();
    }

    private Map<String , Field> cacheMap ;

    @Override
    public long insert(T t) {
        Map<String , String > map = getValue(t);
        ContentValues contentValues = mapToContent(map);

        return sqLiteDatabase.insert(tableName , null , contentValues);
    }

    private ContentValues mapToContent(Map<String , String > map){
        ContentValues contentValues = new ContentValues();
        Iterator<String> iterator = map.keySet().iterator();
        while(iterator.hasNext()){
            String key = iterator.next();
            String value = map.get(key);
            contentValues.put(key , value);
        }
        return contentValues;
    }

    private Map<String , String>  getValue(T t){
        Map<String , String>  map = new HashMap<>();
        Iterator<String> iterator = cacheMap.keySet().iterator();
        while(iterator.hasNext()){
            String fieldName = iterator.next();
            Field field = cacheMap.get(fieldName);
            field.setAccessible(true);
            Object o  = null;
            try {
                o = field.get(t);
                map.put(fieldName , o.toString());
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }

        return map;
    }

    private String createTableStr(){
        StringBuffer stringBuffer = new StringBuffer();
        stringBuffer.append("create table if not exists ");
        stringBuffer.append(tableName+"(");
        Field [] fields = clazz.getDeclaredFields();
        for(Field field : fields){
            DBField annotation = field.getAnnotation(DBField.class);
            if(annotation== null){
                continue;
            }
            String fieldName = annotation.value();
            Class<?> type = field.getType();
            if(type == String.class){
                stringBuffer.append(fieldName+" TEXT , ");
            }else if(type == Integer.class){
                stringBuffer.append(fieldName+" INTEGER,");
            }else if(type == Long.class){
                stringBuffer.append(fieldName+" LONG,");
            }

            if(stringBuffer.charAt(stringBuffer.length() - 1)== ','){
                stringBuffer.deleteCharAt(stringBuffer.length() -1);
            }

        }
        stringBuffer.append(" )");
        return stringBuffer.toString();
    }

    private void initCacheMap(){
        Field [] fields = clazz.getDeclaredFields();
        for(Field field : fields){
            DBField annotation = field.getAnnotation(DBField.class);
            if(annotation== null){
                continue;
            }
            String fieldName = annotation.value();
            cacheMap.put(fieldName,field);
        }
    }
}
