package io.goooler.demoapp.base;

import android.annotation.SuppressLint;
import android.app.Application;
import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.database.sqlite.SQLiteDatabase;
import android.os.Handler;

import com.squareup.leakcanary.LeakCanary;

import io.goooler.demoapp.model.Constants;

/**
 * 封装通用方法和一些初始化的动作
 */
public class BaseApplication extends Application {
    @SuppressLint("StaticFieldLeak")
    private static Context context;
    private static Handler handler;
    private static DaoSession daoSession;

    @Override
    public void onCreate() {
        super.onCreate();
        initGlobalObject();
    }

    /**
     * 这里注意此方法仅在模拟器环境下会回调
     */
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
        LeakCanary.install(this);
    }

    /**
     * 判断包是否为 debug
     */
    public static boolean isDebuggable() {
        try {
            ApplicationInfo info = context.getApplicationInfo();
            return (info.flags & ApplicationInfo.FLAG_DEBUGGABLE) != 0;
        } catch (Exception e) {
            // do nothing
        }
        return false;
    }

    /**
     * 应用结束时销毁全局对象
     * 真机环境需要在 mainActivity.onDestroy() 执行
     */
    public static void destroyGlobalObject() {
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

    /**
     * 获取全局 context
     */
    public static Context getContext() {
        return context;
    }

    /**
     * 获取全局 handler
     */
    public static Handler getHandler() {
        return handler;
    }

    /**
     * 获取全局 daoSession
     */
    public static DaoSession getDaoSession() {
        return daoSession;
    }
}