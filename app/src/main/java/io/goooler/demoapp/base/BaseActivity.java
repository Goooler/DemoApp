package io.goooler.demoapp.base;

import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import io.goooler.demoapp.model.Constants;
import io.goooler.demoapp.util.LogUtil;

/**
 * 基类，封装通用方法
 */
public abstract class BaseActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // activity 入栈日志记录
        LogUtil.d(Constants.BASE_ACTIVITY, getClass().getSimpleName());
        // activity 入栈 List<Activity> 记录
        ActivityCollector.addActivity(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        // activity 出栈 List<Activity> 移除
        ActivityCollector.removeActivity(this);
    }

    /**
     * findViewById 泛型自动造型封装
     *
     * @return (T) view
     */
    @SuppressWarnings("unchecked")
    protected <T extends View> T find(int resId) {
        return (T) super.findViewById(resId);
    }
}
