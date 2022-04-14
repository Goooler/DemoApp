package io.goooler.demoapp.common.util;

import androidx.annotation.ColorInt;
import androidx.annotation.NonNull;
import androidx.databinding.BindingAdapter;
import com.scwang.smart.refresh.classics.ClassicsAbstract;
import com.scwang.smart.refresh.footer.ClassicsFooter;
import com.scwang.smart.refresh.header.ClassicsHeader;
import com.scwang.smart.refresh.layout.SmartRefreshLayout;
import com.scwang.smart.refresh.layout.api.RefreshFooter;
import com.scwang.smart.refresh.layout.api.RefreshHeader;

public class CommonBindingAdapters {

  @BindingAdapter("binding_srl_refreshFinish")
  public static void bindingFinishRefresh(@NonNull SmartRefreshLayout refreshLayout, boolean isFinish) {
    if (isFinish) refreshLayout.finishRefresh();
  }

  @BindingAdapter("binding_srl_loadMoreFinish")
  public static void bindingFinishLoadMore(@NonNull SmartRefreshLayout refreshLayout, boolean isFinish) {
    if (isFinish) refreshLayout.finishLoadMore();
  }

  @BindingAdapter("binding_srl_enableLoadMore")
  public static void bindingEnableLoadMore(@NonNull SmartRefreshLayout refreshLayout, boolean enable) {
    refreshLayout.setEnableLoadMore(enable);
  }

  @BindingAdapter("binding_srl_enableRefresh")
  public static void bindingEnableRefresh(@NonNull SmartRefreshLayout refreshLayout, boolean enable) {
    refreshLayout.setEnableRefresh(enable);
  }

  @BindingAdapter("binding_srl_noMore")
  public static void bindingNoMoreData(@NonNull SmartRefreshLayout refreshLayout, boolean haveNoMore) {
    refreshLayout.setNoMoreData(haveNoMore);
  }

  @BindingAdapter("binding_srl_headerEmpty")
  public static void bindingHeaderEmpty(@NonNull SmartRefreshLayout refreshLayout, boolean isEmpty) {
    RefreshHeader refreshHeader = refreshLayout.getRefreshHeader();
    if (refreshHeader instanceof ClassicsAbstract) {
      ClassicsAbstract<?> classicsAbstract = (ClassicsAbstract<?>) refreshHeader;
      int childCount = classicsAbstract.getChildCount();
      for (int i = 0; i < childCount; i++) {
        classicsAbstract.getChildAt(i).setAlpha(isEmpty ? 0f : 1f);
      }
    }
  }

  @BindingAdapter("binding_srl_footerEmpty")
  public static void bindingFooterEmpty(@NonNull SmartRefreshLayout refreshLayout, boolean isEmpty) {
    RefreshFooter refreshFooter = refreshLayout.getRefreshFooter();
    if (refreshFooter instanceof ClassicsAbstract) {
      ClassicsAbstract<?> classicsAbstract = (ClassicsAbstract<?>) refreshFooter;
      int childCount = classicsAbstract.getChildCount();
      for (int i = 0; i < childCount; i++) {
        classicsAbstract.getChildAt(i).setAlpha(isEmpty ? 0f : 1f);
      }
    }
  }

  @BindingAdapter("binding_srl_headerPrimaryColor")
  public static void bindingHeaderPrimaryColor(@NonNull SmartRefreshLayout refreshLayout, @ColorInt int color) {
    if (refreshLayout.getRefreshHeader() instanceof ClassicsAbstract) {
      ((ClassicsAbstract<?>) refreshLayout.getRefreshHeader()).setPrimaryColor(color);
    } else {
      ClassicsHeader classicsHeader = new ClassicsHeader(refreshLayout.getContext());
      classicsHeader.setPrimaryColor(color);
      refreshLayout.setRefreshHeader(classicsHeader);
    }
  }

  @BindingAdapter("binding_srl_footerPrimaryColor")
  public static void bindingFooterPrimaryColor(@NonNull SmartRefreshLayout refreshLayout, @ColorInt int color) {
    if (refreshLayout.getRefreshFooter() instanceof ClassicsAbstract) {
      ((ClassicsAbstract<?>) refreshLayout.getRefreshFooter()).setPrimaryColor(color);
    } else {
      ClassicsFooter classicsHeader = new ClassicsFooter(refreshLayout.getContext());
      classicsHeader.setPrimaryColor(color);
      refreshLayout.setRefreshFooter(classicsHeader);
    }
  }

  @BindingAdapter("binding_srl_headerAccentColor")
  public static void bindingHeaderAccentColor(@NonNull SmartRefreshLayout refreshLayout, @ColorInt int color) {
    if (refreshLayout.getRefreshHeader() instanceof ClassicsAbstract) {
      ((ClassicsAbstract<?>) refreshLayout.getRefreshHeader()).setAccentColor(color);
    } else {
      ClassicsHeader classicsHeader = new ClassicsHeader(refreshLayout.getContext());
      classicsHeader.setAccentColor(color);
      refreshLayout.setRefreshHeader(classicsHeader);
    }
  }

  @BindingAdapter("binding_srl_footerAccentColor")
  public static void bindingFooterAccentColor(@NonNull SmartRefreshLayout refreshLayout, @ColorInt int color) {
    if (refreshLayout.getRefreshFooter() instanceof ClassicsAbstract) {
      ((ClassicsAbstract<?>) refreshLayout.getRefreshFooter()).setAccentColor(color);
    } else {
      ClassicsFooter classicsHeader = new ClassicsFooter(refreshLayout.getContext());
      classicsHeader.setAccentColor(color);
      refreshLayout.setRefreshFooter(classicsHeader);
    }
  }
}
