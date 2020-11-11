package io.goooler.demoapp.adapter.rv.core

import androidx.annotation.LayoutRes
import androidx.databinding.ViewDataBinding

/**
 * Created on 2020/10/21.
 *
 * A class model(same viewType) corresponds to a delegate object.
 *
 * @author feling
 * @version 1.0.0
 * @since 1.0.0
 */
interface ViewTypeDelegate<DB : ViewDataBinding, M : IVhModelType> {

    /**
     * The delegate takes effect when this.getViewType() == model.getViewType().
     *
     * @[LayoutRes]
     */
    val viewType: Int

    /**
     * What to do when creating the viewHolder.
     *
     * @param binding ViewDataBinding
     */
    fun onCreateVH(binding: DB)

    /**
     * What to do when binding the viewHolder.
     *
     * @param binding ViewDataBinding
     * @param model model
     */
    fun onBindVH(binding: DB, model: M)
}