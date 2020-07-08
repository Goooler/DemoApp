package io.goooler.demoapp.adapter.base

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.RecyclerView

/**
 * Created on 2019/08/22.
 *
 * @author feling
 * @version 1.0.0
 * @since 1.0.0
 */
abstract class BaseAdapter<T : IModelType> : RecyclerView.Adapter<BaseViewHolder>() {

    private val ivdManager: ViewTypeDelegateManager<T> = ViewTypeDelegateManager()

    private var fix: IFix<T>? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder {
        val holder = createVH(parent, viewType)
        onCreateVHForAll(holder.binding)
        ivdManager.onCreateVH(holder.binding, viewType)
        return holder
    }

    override fun onBindViewHolder(holder: BaseViewHolder, position: Int) {
        val item = list[position]
        onBindVHForAll(holder.binding, item)
        ivdManager.onBindVH(holder.binding, item)
        holder.binding.executePendingBindings()
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun getItemViewType(position: Int): Int {
        return list[position].getViewType()
    }

    override fun onAttachedToRecyclerView(recyclerView: RecyclerView) {
        addDelegate(ivdManager)
        super.onAttachedToRecyclerView(recyclerView)
    }

    override fun onDetachedFromRecyclerView(recyclerView: RecyclerView) {
        ivdManager.clear()
        super.onDetachedFromRecyclerView(recyclerView)
    }

    protected abstract val list: List<T>

    abstract fun onCreateVHForAll(binding: ViewDataBinding)

    abstract fun onBindVHForAll(binding: ViewDataBinding, m: T)

    /**
     * 初始化各种 viewType 处理委托。添加到 Manager 中。
     */
    protected open fun addDelegate(manager: ViewTypeDelegateManager<T>) {}

    protected open fun createVH(parent: ViewGroup, viewType: Int): BaseViewHolder {
        val binding = DataBindingUtil.inflate<ViewDataBinding>(
            LayoutInflater.from(parent.context), viewType, parent, false
        )
        return BaseViewHolder(binding)
    }

    protected fun List<T>.multiList(): List<T> {
        return fix?.fix(this) ?: this
    }

    fun setIFix(iMulti: IFix<T>) {
        fix = iMulti
    }

    interface IFix<T> {
        fun fix(list: List<T>): List<T>
    }
}