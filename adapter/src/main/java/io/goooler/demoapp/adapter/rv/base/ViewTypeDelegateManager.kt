package io.goooler.demoapp.adapter.rv.base

import android.util.SparseArray
import androidx.core.util.isNotEmpty
import androidx.databinding.ViewDataBinding

/**
 * Created on 2019/08/31.
 * ViewTypeDelegate 的统一管理类。
 *
 * @author feling
 * @version 1.0.0
 * @since 1.0.0
 */
@Suppress("unused")
class ViewTypeDelegateManager<M : IModelType> {

    private val mIVDs: SparseArray<ViewTypeDelegate<ViewDataBinding, M>> = SparseArray()

    /**
     * 当创建 ViewHolder 时 getViewType = viewType 的 ViewTypeDelegate 响应执行 onCreateVH 方法。
     * @param binding ViewDataBinding
     * @param viewType viewType
     */
    fun onCreateVH(binding: ViewDataBinding, viewType: Int) {
        if (mIVDs.isNotEmpty())
            mIVDs.get(viewType)?.onCreateVH(binding)
    }

    /**
     * 当绑定 ViewHolder 时 getViewType = viewType 的 ViewTypeDelegate 响应执行 onBindVH 方法。
     * @param binding ViewDataBinding
     * @param model model
     */
    fun onBindVH(binding: ViewDataBinding, model: M) {
        if (mIVDs.isNotEmpty())
            mIVDs.get(model.viewType)?.onBindVH(binding, model)
    }

    /**
     * 添加 ItemViewDelegate。
     *
     * @param ivd ViewTypeDelegate
     */
    @Suppress("UNCHECKED_CAST")
    fun <X : ViewDataBinding, Y : M> add(ivd: ViewTypeDelegate<X, Y>) {
        mIVDs.put(ivd.viewType, ivd as ViewTypeDelegate<ViewDataBinding, M>)
    }

    /**
     * 消除所有 ItemViewDelegate。
     */
    fun clear() {
        mIVDs.clear()
    }
}