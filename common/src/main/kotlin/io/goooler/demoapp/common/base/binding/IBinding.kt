package io.goooler.demoapp.common.base.binding

import android.view.LayoutInflater
import androidx.annotation.MainThread
import androidx.viewbinding.ViewBinding
import java.lang.reflect.Method
import java.lang.reflect.ParameterizedType

internal sealed interface IBinding<VB : ViewBinding> {
  val binding: VB

  fun inflateBinding(inflater: LayoutInflater): VB {
    var method: Method?
    var clazz: Class<*> = javaClass
    while (clazz.superclass != null) {
      method = clazz.filterBindingMethod()
      if (method == null) {
        clazz = clazz.superclass
      } else {
        @Suppress("UNCHECKED_CAST")
        return method.invoke(null, inflater) as VB
      }
    }
    error("No Binding type argument found.")
  }

  private fun Class<*>.filterBindingMethod(): Method? {
    return (genericSuperclass as? ParameterizedType)?.actualTypeArguments
      ?.asSequence()
      ?.filterIsInstance<Class<*>>()
      ?.firstOrNull { it.simpleName.endsWith("Binding") }
      ?.getDeclaredMethod("inflate", LayoutInflater::class.java)
      ?.also { it.isAccessible = true }
  }
}

internal sealed interface IBindingFragment<VB : ViewBinding> : IBinding<VB> {

  @MainThread
  fun initOnce() {
  }
}
