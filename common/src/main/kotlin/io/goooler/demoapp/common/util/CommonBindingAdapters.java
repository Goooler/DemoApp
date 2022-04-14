package io.goooler.demoapp.common.util;

import androidx.annotation.ColorInt;
import androidx.databinding.BindingAdapter;
import com.scwang.smart.refresh.classics.ClassicsAbstract;
import com.scwang.smart.refresh.footer.ClassicsFooter;
import com.scwang.smart.refresh.header.ClassicsHeader;
import com.scwang.smart.refresh.layout.SmartRefreshLayout;
import com.scwang.smart.refresh.layout.api.RefreshFooter;
import com.scwang.smart.refresh.layout.api.RefreshHeader;

public class CommonBindingAdapters {

  @BindingAdapter("binding_srl_refreshFinish")
  public static void bindingFinishRefresh(SmartRefreshLayout smartRefreshLayout, Boolean isFinish) {
    if (isFinish) smartRefreshLayout.finishRefresh();
  }

  @BindingAdapter("binding_srl_loadMoreFinish")
  public static void bindingFinishLoadMore(SmartRefreshLayout smartRefreshLayout, Boolean isFinish) {
    if (isFinish) smartRefreshLayout.finishLoadMore();
  }

  @BindingAdapter("binding_srl_enableLoadMore")
  public static void bindingEnableLoadMore(SmartRefreshLayout smartRefreshLayout, Boolean enable) {
    smartRefreshLayout.setEnableLoadMore(enable);
  }

  @BindingAdapter("binding_srl_enableRefresh")
  public static void bindingEnableRefresh(SmartRefreshLayout smartRefreshLayout, Boolean enable) {
    smartRefreshLayout.setEnableRefresh(enable);
  }

  @BindingAdapter("binding_srl_noMore")
  public static void bindingNoMoreData(SmartRefreshLayout smartRefreshLayout, Boolean haveNoMore) {
    smartRefreshLayout.setNoMoreData(haveNoMore);
  }

  @BindingAdapter("binding_srl_headerEmpty")
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

  @BindingAdapter("binding_srl_footerEmpty")
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

  @BindingAdapter("binding_srl_headerPrimaryColor")
  public static void bindingHeaderPrimaryColor(SmartRefreshLayout smartRefreshLayout, @ColorInt int color) {
    if (smartRefreshLayout.getRefreshHeader() instanceof ClassicsAbstract) {
      ((ClassicsAbstract<?>) smartRefreshLayout.getRefreshHeader()).setPrimaryColor(color);
    } else {
      ClassicsHeader classicsHeader = new ClassicsHeader(smartRefreshLayout.getContext());
      classicsHeader.setPrimaryColor(color);
      smartRefreshLayout.setRefreshHeader(classicsHeader);
    }
  }

  @BindingAdapter("binding_srl_footerPrimaryColor")
  public static void bindingFooterPrimaryColor(SmartRefreshLayout smartRefreshLayout, @ColorInt int color) {
    if (smartRefreshLayout.getRefreshFooter() instanceof ClassicsAbstract) {
      ((ClassicsAbstract<?>) smartRefreshLayout.getRefreshFooter()).setPrimaryColor(color);
    } else {
      ClassicsFooter classicsHeader = new ClassicsFooter(smartRefreshLayout.getContext());
      classicsHeader.setPrimaryColor(color);
      smartRefreshLayout.setRefreshFooter(classicsHeader);
    }
  }

  @BindingAdapter("binding_srl_headerAccentColor")
  public static void bindingHeaderAccentColor(SmartRefreshLayout smartRefreshLayout, @ColorInt int color) {
    if (smartRefreshLayout.getRefreshHeader() instanceof ClassicsAbstract) {
      ((ClassicsAbstract<?>) smartRefreshLayout.getRefreshHeader()).setAccentColor(color);
    } else {
      ClassicsHeader classicsHeader = new ClassicsHeader(smartRefreshLayout.getContext());
      classicsHeader.setAccentColor(color);
      smartRefreshLayout.setRefreshHeader(classicsHeader);
    }
  }

  @BindingAdapter("binding_srl_footerAccentColor")
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
