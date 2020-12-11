package io.goooler.demoapp.adapter.rv.core

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.annotation.IntRange
import androidx.annotation.LayoutRes
import androidx.annotation.MainThread
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.RecyclerView

/**
 * Created on 2020/10/21.
 *
 * Please let your [RecyclerView.Adapter] implements IExtAdapter.
 *
 * @author feling
 * @version 1.0.0
 * @since 1.0.0
 */
@MainThread
interface IRvAdapter<M : IVhModelType> {

  /**
   * What to do when creating the viewHolder for all.
   */
  fun onCreateVHForAll(binding: ViewDataBinding)

  /**
   * What to do when binding the viewHolder for all.
   */
  fun onBindVHForAll(binding: ViewDataBinding, model: M)

  /**
   * Create BaseViewHolder.
   */
  fun createVH(parent: ViewGroup, @LayoutRes viewType: Int): BindingViewHolder {
    val binding = DataBindingUtil.inflate<ViewDataBinding>(
      LayoutInflater.from(parent.context),
      viewType,
      parent,
      false
    )
    return BindingViewHolder(binding)
  }

  /**
   * Init ViewTypeDelegateManager. You can add VTDs.
   */
  fun initManager(manager: ViewTypeDelegateManager<M>) {}

  /**
   * Get item by position.
   */
  fun getModel(@IntRange(from = 0) position: Int): M?
}
