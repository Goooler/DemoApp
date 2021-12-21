package io.goooler.demoapp.common.base.theme

import android.content.res.Resources
import android.os.Bundle
import androidx.annotation.MainThread
import com.blankj.utilcode.util.AdaptScreenUtils
import com.blankj.utilcode.util.BarUtils
import com.blankj.utilcode.util.ScreenUtils
import io.goooler.demoapp.base.core.BaseActivity

abstract class BaseThemeActivity : BaseActivity(), ITheme {

  override fun getResources(): Resources {
    return if (ScreenUtils.isPortrait())
      AdaptScreenUtils.adaptWidth(super.getResources(), 360)
    else
      AdaptScreenUtils.adaptHeight(super.getResources(), 640)
  }

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    BarUtils.transparentStatusBar(this)
  }

  @MainThread
  override fun showLoading() {
  }

  @MainThread
  override fun hideLoading() {
  }
}
