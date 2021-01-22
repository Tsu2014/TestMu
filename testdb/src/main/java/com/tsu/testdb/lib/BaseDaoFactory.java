package com.tsu.testdb.lib;

import android.database.sqlite.SQLiteDatabase;

import java.io.File;

/**
 * create database
 */
public class BaseDaoFactory {

    private static final String TAG = "BaseDaoFactory";
    public static final String PATH = "/data/data/com.tsu.testdb/database/database.db";
    private static BaseDaoFactory baseDaoFactory = new BaseDaoFactory();
    private String path;
    SQLiteDatabase sqLiteDatabase;
    private BaseDaoFactory(){

    }

    /**
     *
     * @param path
     * //data/data/packageName/data.db
     */
    public void init(String path){
        this.path = path;
        File file = new File(this.path);
        if(!file.getParentFile().exists()){
            file.getParentFile().mkdirs();
        }

        sqLiteDatabase = SQLiteDatabase.openOrCreateDatabase(this.path , null);
    }

    public static BaseDaoFactory getInstance(){
        return baseDaoFactory;
    }

    public<T> BaseDao<T> getBaseDao(Class<T> clazz){
        return new BaseDao<T>(clazz , sqLiteDatabase);
    }

}
