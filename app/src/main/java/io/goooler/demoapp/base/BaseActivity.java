package io.goooler.demoapp.base;

import android.os.Bundle;

import androidx.annotation.StringRes;
import androidx.appcompat.app.AppCompatActivity;

import io.goooler.demoapp.model.Constants;
import io.goooler.demoapp.util.LogUtil;
import io.goooler.demoapp.util.ToastUtil;

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

    protected void showToast(@StringRes int textId) {
        ToastUtil.showToast(textId);
    }

    protected void showToast(String text) {
        ToastUtil.showToast(text);
    }
}
