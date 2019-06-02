package io.goooler.demoapp.base;

import android.app.Application;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.os.Handler;

import io.goooler.demoapp.model.Constants;
import io.goooler.demoapp.model.DaoMaster;
import io.goooler.demoapp.model.DaoSession;

/**
 * 封装通用方法和一些初始化的动作
 */
public class BaseApplication extends Application {
    private static Context context;
    private static Handler handler;
    private static DaoSession daoSession;

    @Override
    public void onCreate() {
        super.onCreate();
        initGlobalObject();
    }

    @Override
    public void onTerminate() {
        super.onTerminate();
        destroyGlobalObject();
    }

    /**
     * 应用启动时初始化全局对象
     */
    private void initGlobalObject() {
        context = getApplicationContext();
        handler = new Handler();
        initGreenDao();
    }

    /**
     * 应用结束时销毁全局对象
     */
    private void destroyGlobalObject() {
        handler.removeCallbacksAndMessages(null);
    }

    /**
     * 初始化 GreenDao 相关
     */
    private void initGreenDao() {
        DaoMaster.DevOpenHelper helper = new DaoMaster.DevOpenHelper(this, Constants.DB_NAME);
        SQLiteDatabase db = helper.getWritableDatabase();
        DaoMaster daoMaster = new DaoMaster(db);
        daoSession = daoMaster.newSession();
    }

    public static Context getContext() {
        return context;
    }

    public static Handler getHandler() {
        return handler;
    }

    public static DaoSession getDaoSession() {
        return daoSession;
    }
}