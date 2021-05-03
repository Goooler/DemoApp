package io.goooler.demoapp.common.base

import android.content.res.Resources
import android.os.Bundle
import androidx.annotation.CallSuper
import androidx.databinding.ViewDataBinding
import com.blankj.utilcode.util.AdaptScreenUtils
import com.blankj.utilcode.util.BarUtils
import com.blankj.utilcode.util.ScreenUtils
import io.goooler.demoapp.base.core.BaseActivity
import io.goooler.demoapp.common.util.inflateBinding

abstract class BaseThemeActivity<VB : ViewDataBinding> :
  BaseActivity(),
  ITheme {

  protected lateinit var binding: VB

  @CallSuper
  override fun getResources(): Resources {
    return if (ScreenUtils.isPortrait())
      AdaptScreenUtils.adaptWidth(super.getResources(), 360)
    else
      AdaptScreenUtils.adaptHeight(super.getResources(), 640)
  }

  @CallSuper
  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    binding = (inflateBinding(layoutInflater) as VB).also {
      it.lifecycleOwner = this
      setContentView(it.root)
    }
    BarUtils.transparentStatusBar(this)
  }

  override fun showLoading() {}

  override fun hideLoading() {}
}
