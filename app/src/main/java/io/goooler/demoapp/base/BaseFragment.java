package io.goooler.demoapp.base;

import android.view.View;

import androidx.fragment.app.Fragment;

/**
 * 基类，封装通用方法
 */

public abstract class BaseFragment extends Fragment {

    public <T extends View> T find(View view, int resId) {
        return (T) view.findViewById(resId);
    }

    protected void initView(View rootView) {
    }
}
