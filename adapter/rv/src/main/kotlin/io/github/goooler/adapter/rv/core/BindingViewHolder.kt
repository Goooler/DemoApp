package io.github.goooler.adapter.rv.core

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.RecyclerView

/**
 * Created on 2020/10/21.
 *
 * Base ViewHolder. Use [ViewDataBinding].
 *
 * @author feling
 * @version 1.0.0
 * @since 1.0.0
 */
class BindingViewHolder(val binding: ViewDataBinding) : RecyclerView.ViewHolder(binding.root) {

  companion object {
    fun create(parent: ViewGroup, @LayoutRes viewType: Int): BindingViewHolder {
      val binding = DataBindingUtil.inflate<ViewDataBinding>(
        LayoutInflater.from(parent.context),
        viewType,
        parent,
        false,
      )
      return BindingViewHolder(binding)
    }
  }
}
