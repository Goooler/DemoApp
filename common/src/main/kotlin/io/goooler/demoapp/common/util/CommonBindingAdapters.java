package io.goooler.demoapp.common.util;

import androidx.annotation.ColorInt;
import com.scwang.smart.refresh.classics.ClassicsAbstract;
import com.scwang.smart.refresh.footer.ClassicsFooter;
import com.scwang.smart.refresh.header.ClassicsHeader;
import com.scwang.smart.refresh.layout.SmartRefreshLayout;
import com.scwang.smart.refresh.layout.api.RefreshFooter;
import com.scwang.smart.refresh.layout.api.RefreshHeader;

public class CommonBindingAdapters {
  public static void bindingFinishRefresh(SmartRefreshLayout smartRefreshLayout, Boolean isFinish) {
    if (isFinish) smartRefreshLayout.finishRefresh();
  }

  public static void bindingFinishLoadMore(SmartRefreshLayout smartRefreshLayout, Boolean isFinish) {
    if (isFinish) smartRefreshLayout.finishLoadMore();
  }

  public static void bindingEnableLoadMore(SmartRefreshLayout smartRefreshLayout, Boolean enable) {
    smartRefreshLayout.setEnableLoadMore(enable);
  }

  public static void bindingEnableRefresh(SmartRefreshLayout smartRefreshLayout, Boolean enable) {
    smartRefreshLayout.setEnableRefresh(enable);
  }

  public static void bindingNoMoreData(SmartRefreshLayout smartRefreshLayout, Boolean haveNoMore) {
    smartRefreshLayout.setNoMoreData(haveNoMore);
  }

  public static void bindingHeaderEmpty(SmartRefreshLayout smartRefreshLayout, Boolean isEmpty) {
    RefreshHeader refreshHeader = smartRefreshLayout.getRefreshHeader();
    if (refreshHeader instanceof ClassicsAbstract) {
      ClassicsAbstract<?> classicsAbstract = (ClassicsAbstract<?>) refreshHeader;
      int childCount = classicsAbstract.getChildCount();
      for (int i = 0; i < childCount; i++) {
        classicsAbstract.getChildAt(i).setAlpha(isEmpty ? 0f : 1f);
      }
    }
  }

  public static void bindingFooterEmpty(SmartRefreshLayout smartRefreshLayout, Boolean isEmpty) {
    RefreshFooter refreshFooter = smartRefreshLayout.getRefreshFooter();
    if (refreshFooter instanceof ClassicsAbstract) {
      ClassicsAbstract<?> classicsAbstract = (ClassicsAbstract<?>) refreshFooter;
      int childCount = classicsAbstract.getChildCount();
      for (int i = 0; i < childCount; i++) {
        classicsAbstract.getChildAt(i).setAlpha(isEmpty ? 0f : 1f);
      }
    }
  }

  public static void bindingHeaderPrimaryColor(SmartRefreshLayout smartRefreshLayout, @ColorInt int color) {
    if (smartRefreshLayout.getRefreshHeader() instanceof ClassicsAbstract) {
      ((ClassicsAbstract<?>) smartRefreshLayout.getRefreshHeader()).setPrimaryColor(color);
    } else {
      ClassicsHeader classicsHeader = new ClassicsHeader(smartRefreshLayout.getContext());
      classicsHeader.setPrimaryColor(color);
      smartRefreshLayout.setRefreshHeader(classicsHeader);
    }
  }

  public static void bindingFooterPrimaryColor(SmartRefreshLayout smartRefreshLayout, @ColorInt int color) {
    if (smartRefreshLayout.getRefreshFooter() instanceof ClassicsAbstract) {
      ((ClassicsAbstract<?>) smartRefreshLayout.getRefreshFooter()).setPrimaryColor(color);
    } else {
      ClassicsFooter classicsHeader = new ClassicsFooter(smartRefreshLayout.getContext());
      classicsHeader.setPrimaryColor(color);
      smartRefreshLayout.setRefreshFooter(classicsHeader);
    }
  }

  public static void bindingHeaderAccentColor(SmartRefreshLayout smartRefreshLayout, @ColorInt int color) {
    if (smartRefreshLayout.getRefreshHeader() instanceof ClassicsAbstract) {
      ((ClassicsAbstract<?>) smartRefreshLayout.getRefreshHeader()).setAccentColor(color);
    } else {
      ClassicsHeader classicsHeader = new ClassicsHeader(smartRefreshLayout.getContext());
      classicsHeader.setAccentColor(color);
      smartRefreshLayout.setRefreshHeader(classicsHeader);
    }
  }

  public static void bindingFooterAccentColor(SmartRefreshLayout smartRefreshLayout, @ColorInt int color) {
    if (smartRefreshLayout.getRefreshFooter() instanceof ClassicsAbstract) {
      ((ClassicsAbstract<?>) smartRefreshLayout.getRefreshFooter()).setAccentColor(color);
    } else {
      ClassicsFooter classicsHeader = new ClassicsFooter(smartRefreshLayout.getContext());
      classicsHeader.setAccentColor(color);
      smartRefreshLayout.setRefreshFooter(classicsHeader);
    }
  }
}
