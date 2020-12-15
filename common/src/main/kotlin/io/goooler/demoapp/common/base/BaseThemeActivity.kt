package io.goooler.demoapp.common.base

import android.content.res.Resources
import android.os.Bundle
import androidx.annotation.LayoutRes
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import com.blankj.utilcode.util.AdaptScreenUtils
import com.blankj.utilcode.util.BarUtils
import io.goooler.demoapp.base.core.BaseActivity

@Suppress("MemberVisibilityCanBePrivate")
abstract class BaseThemeActivity<VB : ViewDataBinding> : BaseActivity(), ITheme {

  val originalResources: Resources get() = super.getResources()

  /**
   * @[LayoutRes]
   */
  protected abstract val layoutId: Int

  protected lateinit var binding: VB

  override fun getResources(): Resources = AdaptScreenUtils.adaptWidth(originalResources, 360)

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    binding = DataBindingUtil.setContentView<VB>(this, layoutId).also {
      it.lifecycleOwner = this
    }
    BarUtils.transparentStatusBar(this)
  }

  override fun showLoading() {
  }

  override fun hideLoading() {
  }
}
