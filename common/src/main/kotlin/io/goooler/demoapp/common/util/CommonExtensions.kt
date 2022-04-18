@file:Suppress("unused")
@file:JvmName("CommonExtensionUtil")

package io.goooler.demoapp.common.util

import android.graphics.Bitmap
import android.graphics.drawable.Drawable
import android.view.LayoutInflater
import android.widget.TextView
import androidx.annotation.AnyThread
import androidx.annotation.ColorInt
import androidx.annotation.ColorRes
import androidx.annotation.Dimension
import androidx.annotation.DrawableRes
import androidx.annotation.MainThread
import androidx.annotation.PluralsRes
import androidx.annotation.Px
import androidx.annotation.StringRes
import androidx.core.widget.doAfterTextChanged
import androidx.lifecycle.LifecycleOwner
import androidx.recyclerview.widget.AsyncDifferConfig
import androidx.recyclerview.widget.DiffUtil
import androidx.viewbinding.ViewBinding
import com.blankj.utilcode.util.AdaptScreenUtils
import com.blankj.utilcode.util.ColorUtils
import com.blankj.utilcode.util.ImageUtils
import com.blankj.utilcode.util.ResourceUtils
import com.blankj.utilcode.util.SPUtils
import com.blankj.utilcode.util.SizeUtils
import com.blankj.utilcode.util.StringUtils
import com.google.android.material.textfield.TextInputLayout
import com.scwang.smart.refresh.layout.SmartRefreshLayout
import io.goooler.demoapp.base.core.BaseViewModel
import io.goooler.demoapp.base.util.Dp
import io.goooler.demoapp.base.util.Pt
import io.goooler.demoapp.base.util.Sp
import io.goooler.demoapp.base.util.ToastUtil
import io.goooler.demoapp.common.BuildConfig
import io.goooler.demoapp.common.CommonApplication
import io.goooler.demoapp.common.type.SpKeys
import java.lang.reflect.ParameterizedType
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.asExecutor

typealias SpHelper = SPUtils

val isDebug: Boolean = BuildConfig.DEBUG

inline var isFirstRun: Boolean
  get() = SpHelper.getInstance().getBoolean(SpKeys.FirstRun.key, true)
  set(value) = SpHelper.getInstance().put(SpKeys.FirstRun.key, value)

inline fun debugRun(debug: () -> Unit) {
  if (isDebug) debug()
}

fun Throwable.toast() {
  toString().showToast()
}

@AnyThread
fun @receiver:StringRes Int.showToast() {
  ToastUtil.show(CommonApplication.app, this)
}

@MainThread
fun SmartRefreshLayout.finishRefreshAndLoadMore() {
  finishRefresh()
  finishLoadMore()
}

@MainThread
fun SmartRefreshLayout.enableRefreshAndLoadMore(enable: Boolean = true) {
  setEnableRefresh(enable)
  setEnableLoadMore(enable)
}

@MainThread
fun SmartRefreshLayout.disableRefreshAndLoadMore() {
  enableRefreshAndLoadMore(false)
}

// ---------------------String-------------------------------//

@AnyThread
fun String.showToast() {
  ToastUtil.show(CommonApplication.app, this)
}

fun String.isValidPhoneFormat(): Boolean = startsWith("1") && length == 11

fun String.hidePhoneNumber(): String {
  return replace(Regex("(\\d{3})\\d{4}(\\d{4})"), "$1****$2")
}

// ---------------------Res-------------------------------//

context(BaseViewModel)
fun @receiver:DrawableRes Int.getDrawable(): Drawable? = try {
  ResourceUtils.getDrawable(this)
} catch (e: Exception) {
  e.printStackTrace()
  null
}

context(BaseViewModel)
@ColorInt
fun @receiver:ColorRes Int.getColor(): Int = try {
  ColorUtils.getColor(this)
} catch (e: Exception) {
  e.printStackTrace()
  -1
}

context(BaseViewModel)
fun @receiver:StringRes Int.getString(): String? = try {
  StringUtils.getString(this)
} catch (e: Exception) {
  e.printStackTrace()
  null
}

context(BaseViewModel)
fun @receiver:StringRes Int.getString(vararg formatArgs: Any): String? =
  getString()?.format(formatArgs)

context(BaseViewModel)
fun @receiver:PluralsRes Int.getQuantityString(num: Int): String? = try {
  CommonApplication.app.resources.getQuantityString(this, num, num)
} catch (e: Exception) {
  e.printStackTrace()
  null
}

context(BaseViewModel)
@Px
fun @receiver:Sp Float.sp2px(): Int = SizeUtils.sp2px(this)

context(BaseViewModel)
@Px
fun @receiver:Dp Float.dp2px(): Int = SizeUtils.dp2px(this)

context(BaseViewModel)
@Px
fun @receiver:Pt Float.pt2px(): Int = AdaptScreenUtils.pt2Px(this)

context(BaseViewModel)
@Sp
fun @receiver:Dimension Int.px2sp(): Int = SizeUtils.px2sp(this.toFloat())

context(BaseViewModel)
@Dp
fun @receiver:Dimension Int.px2dp(): Int = SizeUtils.px2dp(this.toFloat())

context(BaseViewModel)
@Pt
fun @receiver:Dimension Int.px2pt(): Int = AdaptScreenUtils.px2Pt(this.toFloat())

context(BaseViewModel)
fun Bitmap.toDrawable(): Drawable = ImageUtils.bitmap2Drawable(this)

context(BaseViewModel)
fun Drawable.toBitmap(): Bitmap = ImageUtils.drawable2Bitmap(this)

// ---------------------View-------------------------------//

fun TextView.hideTextInputLayoutErrorOnTextChange(textInputLayout: TextInputLayout) {
  doAfterTextChanged { textInputLayout.error = null }
}

inline fun <reified T : Any> DiffUtil.ItemCallback<T>.asConfig(): AsyncDifferConfig<T> {
  return AsyncDifferConfig.Builder(this)
    .setBackgroundThreadExecutor(Dispatchers.Default.asExecutor())
    .build()
}

// ---------------------VM & Binding-------------------------------//

@Suppress("UNCHECKED_CAST")
fun <T : ViewBinding> LifecycleOwner.inflateBinding(inflater: LayoutInflater): T {
  return (javaClass.genericSuperclass as ParameterizedType).actualTypeArguments
    .filterIsInstance<Class<T>>()
    .first()
    .getDeclaredMethod("inflate", LayoutInflater::class.java)
    .invoke(null, inflater) as T
}
