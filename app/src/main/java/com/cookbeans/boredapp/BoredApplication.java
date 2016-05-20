package com.cookbeans.boredapp;

import android.app.Application;
import android.content.Context;

import com.litesuits.orm.LiteOrm;

/**
 * Created by dongshujin on 16/5/20.
 */
public class BoredApplication extends Application{

    private static final String DB_NAME = "bored.db";
    public static Context mContext;
    public static LiteOrm dbInstance;

    @Override public void onCreate() {
        super.onCreate();
        mContext = this;
        dbInstance = LiteOrm.newSingleInstance(this, DB_NAME);
        if (BuildConfig.DEBUG) {
            dbInstance.setDebugged(true);
        }
    }

}
