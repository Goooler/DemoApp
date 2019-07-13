package io.goooler.demoapp.base;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

/**
 * 基类，封装通用方法
 */
public abstract class BaseFragment extends Fragment {
    private boolean viewCreated;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = initView(inflater, container, savedInstanceState);
        viewCreated = true;
        return view;
    }

    /**
     * 当页面可见时调用，用于懒加载
     */
    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (viewCreated && isVisibleToUser) {
            loadData();
        }
    }

    /**
     * 默认第一页 fragment 执行，不需要懒加载
     */
    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        if (viewCreated && getUserVisibleHint()) {
            loadData();
        }
    }

    /**
     * onCreateView 中初始化页面的任务放在这里完成，三个参数相同
     *
     * @return 根布局
     */
    protected abstract View initView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState);

    /**
     * 用在懒加载加载数据时
     */
    protected abstract void loadData();
}
