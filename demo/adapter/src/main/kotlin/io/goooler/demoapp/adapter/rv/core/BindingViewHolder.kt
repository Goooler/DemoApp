package io.goooler.demoapp.adapter.rv.core

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
open class BindingViewHolder(val binding: ViewDataBinding) : RecyclerView.ViewHolder(binding.root)
