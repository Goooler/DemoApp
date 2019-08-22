package io.goooler.demoapp.base;

import android.app.Activity;

import java.util.ArrayList;
import java.util.List;

import io.goooler.demoapp.model.Constants;
import io.goooler.demoapp.util.LogUtil;

/**
 * 方便管理 activity
 */
public class ActivityCollector {
    public static List<Activity> activityList = new ArrayList<>();

    /**
     * activity 入栈时记录指针
     */
    public static void addActivity(Activity activity) {
        activityList.add(activity);
    }

    /**
     * activity 出栈时删除指针
     */
    public static void removeActivity(Activity activity) {
        activityList.remove(activity);
    }

    /**
     * 提供给外部一个方法直接干掉所有的 activity
     */
    public static void finishAll() {
        for (Activity activity : activityList) {
            if (!activity.isFinishing()) {
                activity.finish();
            }
        }
        activityList.clear();
        LogUtil.d(Constants.BASE_ACTIVITY, Constants.FINISH_ALL_ACTIVITY);
    }
}