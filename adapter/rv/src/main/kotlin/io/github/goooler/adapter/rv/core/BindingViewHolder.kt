package io.github.goooler.adapter.rv.core

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.RecyclerView

/**
 * Base ViewHolder. Use [ViewDataBinding].
 */
public class BindingViewHolder(public val binding: ViewDataBinding) : RecyclerView.ViewHolder(binding.root) {

  public companion object {
    public fun create(parent: ViewGroup, @LayoutRes viewType: Int): BindingViewHolder {
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
