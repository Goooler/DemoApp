package io.goooler.demoapp.common.base.binding

import android.view.LayoutInflater
import androidx.annotation.MainThread
import androidx.databinding.ViewDataBinding
import androidx.viewbinding.ViewBinding
import java.lang.reflect.ParameterizedType

internal sealed interface IBinding<VB : ViewDataBinding> {
  val binding: VB

  companion object {
    @Suppress("UNCHECKED_CAST")
    internal fun <T : ViewBinding> IBinding<*>.inflateBinding(inflater: LayoutInflater): T {
      return (javaClass.genericSuperclass as ParameterizedType).actualTypeArguments
        .asSequence()
        .filterIsInstance<Class<T>>()
        .first { it.simpleName.endsWith("Binding") }
        .getDeclaredMethod("inflate", LayoutInflater::class.java)
        .also { it.isAccessible = true }
        .invoke(null, inflater) as T
    }
  }
}

internal sealed interface IBindingFragment<VB : ViewDataBinding> : IBinding<VB> {

  @MainThread
  fun initOnce() {
  }
}
