package io.goooler.demoapp.common.base.binding

import android.annotation.SuppressLint
import android.content.pm.ActivityInfo
import android.content.res.Resources
import android.os.Bundle
import android.view.WindowManager
import androidx.viewbinding.ViewBinding
import com.blankj.utilcode.util.AdaptScreenUtils
import com.blankj.utilcode.util.BarUtils
import com.blankj.utilcode.util.ScreenUtils
import io.goooler.demoapp.base.core.BaseActivity

abstract class BaseBindingActivity<VB : ViewBinding> : BaseActivity(), IBinding<VB> {

  override lateinit var binding: VB

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    window.run {
      setBackgroundDrawable(null)
      setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN)
    }
    @SuppressLint("SourceLockedOrientationActivity")
    requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT

    BarUtils.transparentStatusBar(this)

    binding = inflateBinding(layoutInflater).also {
      (it as? androidx.databinding.ViewDataBinding)?.lifecycleOwner = this
      setContentView(it.root)
    }
  }

  @Suppress("MagicNumber")
  override fun getResources(): Resources {
    return if (ScreenUtils.isPortrait()) {
      AdaptScreenUtils.adaptWidth(super.getResources(), 360)
    } else {
      AdaptScreenUtils.adaptHeight(super.getResources(), 640)
    }
  }
}
