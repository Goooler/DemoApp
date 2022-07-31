package io.goooler.demoapp.common.base.binding

import android.annotation.SuppressLint
import android.content.pm.ActivityInfo
import android.content.res.Resources
import android.os.Bundle
import android.view.LayoutInflater
import android.view.WindowManager
import androidx.databinding.ViewDataBinding
import androidx.viewbinding.ViewBinding
import com.blankj.utilcode.util.AdaptScreenUtils
import com.blankj.utilcode.util.BarUtils
import com.blankj.utilcode.util.ScreenUtils
import io.goooler.demoapp.base.core.BaseActivity
import java.lang.reflect.ParameterizedType

abstract class BaseBindingActivity<VB : ViewDataBinding> : BaseActivity(), IBinding<VB> {

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

    binding = (inflateBinding(layoutInflater) as VB).also {
      it.lifecycleOwner = this
      setContentView(it.root)
    }
  }

  override fun getResources(): Resources {
    return if (ScreenUtils.isPortrait())
      AdaptScreenUtils.adaptWidth(super.getResources(), 360)
    else
      AdaptScreenUtils.adaptHeight(super.getResources(), 640)
  }

  companion object {
    @Suppress("UNCHECKED_CAST")
    internal fun <T : ViewBinding> Any.inflateBinding(inflater: LayoutInflater): T {
      return (javaClass.genericSuperclass as ParameterizedType).actualTypeArguments
        .filterIsInstance<Class<T>>()
        .first()
        .getDeclaredMethod("inflate", LayoutInflater::class.java)
        .also { it.isAccessible = true }
        .invoke(null, inflater) as T
    }
  }
}
