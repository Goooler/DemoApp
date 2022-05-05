@file:Suppress("unused")

package io.goooler.demoapp.common.util

import androidx.annotation.ColorInt
import androidx.core.view.forEach
import androidx.databinding.BindingAdapter
import com.scwang.smart.refresh.classics.ClassicsAbstract
import com.scwang.smart.refresh.footer.ClassicsFooter
import com.scwang.smart.refresh.header.ClassicsHeader
import com.scwang.smart.refresh.layout.SmartRefreshLayout

// ------------------------SmartRefreshLayout--------------------------//

@BindingAdapter("binding_srl_refreshFinish")
internal fun SmartRefreshLayout.bindingFinishRefresh(isFinish: Boolean) {
  if (isFinish) finishRefresh()
}

@BindingAdapter("binding_srl_loadMoreFinish")
internal fun SmartRefreshLayout.bindingFinishLoadMore(isFinish: Boolean) {
  if (isFinish) finishLoadMore()
}

@BindingAdapter("binding_srl_enableLoadMore")
internal fun SmartRefreshLayout.bindingEnableLoadMore(enable: Boolean) {
  setEnableLoadMore(enable)
}

@BindingAdapter("binding_srl_enableRefresh")
internal fun SmartRefreshLayout.bindingEnableRefresh(enable: Boolean) {
  setEnableRefresh(enable)
}

@BindingAdapter("binding_srl_noMore")
internal fun SmartRefreshLayout.bindingNoMoreData(haveNoMore: Boolean) {
  setNoMoreData(haveNoMore)
}

@BindingAdapter("binding_srl_headerEmpty")
internal fun SmartRefreshLayout.bindingHeaderEmpty(isEmpty: Boolean) {
  (refreshHeader as? ClassicsAbstract<*>)?.forEach {
    it.alpha = if (isEmpty) 0f else 1f
  }
}

@BindingAdapter("binding_srl_footerEmpty")
internal fun SmartRefreshLayout.bindingFooterEmpty(isEmpty: Boolean) {
  (refreshFooter as? ClassicsAbstract<*>)?.forEach {
    it.alpha = if (isEmpty) 0f else 1f
  }
}

@BindingAdapter("binding_srl_headerPrimaryColor")
internal fun SmartRefreshLayout.bindingHeaderPrimaryColor(@ColorInt color: Int) {
  (refreshHeader as? ClassicsAbstract<*>)?.setPrimaryColor(color)
    ?: setRefreshHeader(ClassicsHeader(context).apply { setPrimaryColor(color) })
}

@BindingAdapter("binding_srl_footerPrimaryColor")
internal fun SmartRefreshLayout.bindingFooterPrimaryColor(@ColorInt color: Int) {
  (refreshFooter as? ClassicsAbstract<*>)?.setPrimaryColor(color)
    ?: setRefreshFooter(ClassicsFooter(context).apply { setPrimaryColor(color) })
}

@BindingAdapter("binding_srl_headerAccentColor")
internal fun SmartRefreshLayout.bindingHeaderAccentColor(@ColorInt color: Int) {
  (refreshHeader as? ClassicsAbstract<*>)?.setAccentColor(color)
    ?: setRefreshHeader(ClassicsHeader(context).apply { setAccentColor(color) })
}

@BindingAdapter("binding_srl_footerAccentColor")
internal fun SmartRefreshLayout.bindingFooterAccentColor(@ColorInt color: Int) {
  (refreshFooter as? ClassicsAbstract<*>)?.setAccentColor(color)
    ?: setRefreshFooter(ClassicsFooter(context).apply { setAccentColor(color) })
}
