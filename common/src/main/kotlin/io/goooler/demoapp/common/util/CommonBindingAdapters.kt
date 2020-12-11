@file:Suppress("unused")

package io.goooler.demoapp.common.util

import androidx.annotation.ColorInt
import androidx.core.view.forEach
import androidx.databinding.BindingAdapter
import com.scwang.smart.refresh.footer.ClassicsFooter
import com.scwang.smart.refresh.header.ClassicsHeader
import com.scwang.smart.refresh.layout.SmartRefreshLayout

// ------------------------SmartRefreshLayout--------------------------//

@BindingAdapter("binding_srl_refreshFinish")
fun SmartRefreshLayout.bindingFinishRefresh(isFinish: Boolean) {
  if (isFinish) finishRefresh()
}

@BindingAdapter("binding_srl_loadMoreFinish")
fun SmartRefreshLayout.bindingFinishLoadMore(isFinish: Boolean) {
  if (isFinish) finishLoadMore()
}

@BindingAdapter("binding_srl_enableLoadMore")
fun SmartRefreshLayout.bindingEnableLoadMore(enable: Boolean) {
  setEnableLoadMore(enable)
}

@BindingAdapter("binding_srl_enableRefresh")
fun SmartRefreshLayout.bindingEnableRefresh(enable: Boolean) {
  setEnableRefresh(enable)
}

@BindingAdapter("binding_srl_noMore")
fun SmartRefreshLayout.bindingNoMoreData(haveNoMore: Boolean) {
  setNoMoreData(haveNoMore)
}

@BindingAdapter("binding_srl_headerEmpty")
fun SmartRefreshLayout.bindingHeaderEmpty(isEmpty: Boolean) {
  (refreshHeader as? ClassicsHeader)?.forEach {
    it.alpha = if (isEmpty) 0f else 1f
  }
}

@BindingAdapter("binding_srl_footerEmpty")
fun SmartRefreshLayout.bindingFooterEmpty(isEmpty: Boolean) {
  (refreshFooter as? ClassicsFooter)?.forEach {
    it.alpha = if (isEmpty) 0f else 1f
  }
}

@BindingAdapter("binding_srl_headerPrimaryColor")
fun SmartRefreshLayout.bindingHeaderPrimaryColor(@ColorInt color: Int) {
  if (refreshHeader == null) {
    setRefreshHeader(ClassicsHeader(context).apply { setPrimaryColor(color) })
  } else {
    (refreshHeader as? ClassicsHeader)?.setPrimaryColor(color)
  }
}

@BindingAdapter("binding_srl_footerPrimaryColor")
fun SmartRefreshLayout.bindingFooterPrimaryColor(@ColorInt color: Int) {
  if (refreshFooter == null) {
    setRefreshFooter(ClassicsFooter(context).apply { setPrimaryColor(color) })
  } else {
    (refreshFooter as? ClassicsFooter)?.setPrimaryColor(color)
  }
}

@BindingAdapter("binding_srl_headerAccentColor")
fun SmartRefreshLayout.bindingHeaderAccentColor(@ColorInt color: Int) {
  if (refreshHeader == null) {
    setRefreshHeader(ClassicsHeader(context).apply { setAccentColor(color) })
  } else {
    (refreshHeader as? ClassicsHeader)?.setAccentColor(color)
  }
}

@BindingAdapter("binding_srl_footerAccentColor")
fun SmartRefreshLayout.bindingFooterAccentColor(@ColorInt color: Int) {
  if (refreshFooter == null) {
    setRefreshFooter(ClassicsFooter(context).apply { setAccentColor(color) })
  } else {
    (refreshFooter as? ClassicsFooter)?.setAccentColor(color)
  }
}
