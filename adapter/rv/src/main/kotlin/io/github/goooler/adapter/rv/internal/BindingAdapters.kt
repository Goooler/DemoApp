package io.github.goooler.adapter.rv.internal

import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import io.github.goooler.adapter.rv.core.IVhModelType

/**
 * Used for generating binding adapters, mark the class as internal to avoid using in Kotlin.
 */
@Suppress("UNCHECKED_CAST")
internal object BindingAdapters {
  @BindingAdapter("binding_rv_dataList")
  @JvmStatic
  fun <M : IVhModelType> RecyclerView.bindingSetList(list: List<M>?) {
    (adapter as? IMutableRvAdapter<M>)?.list = list.orEmpty()
  }

  @BindingAdapter("binding_rv_refreshItems")
  @JvmStatic
  fun <M : IVhModelType> RecyclerView.bindingRefreshItems(vararg items: Triple<Int, M, Any?>) {
    (adapter as? IMutableRvAdapter<M>)?.refreshItems(*items)
  }
}
