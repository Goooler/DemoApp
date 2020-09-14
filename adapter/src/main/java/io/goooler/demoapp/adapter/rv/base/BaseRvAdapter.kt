package io.goooler.demoapp.adapter.rv.base

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
abstract class BaseRvAdapter<T : IModelType> :
    RecyclerView.Adapter<BaseRvAdapter.BaseViewHolder>() {

    private val ivdManager: ViewTypeDelegateManager<T> by lazy(LazyThreadSafetyMode.NONE) {
        ViewTypeDelegateManager()
    }

    var fix: IFix<T>? = null

    protected abstract val modelList: MutableList<T>

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder {
        val holder = createVH(parent, viewType)
        onCreateVHForAll(holder.binding)
        ivdManager.onCreateVH(holder.binding, viewType)
        return holder
    }

    override fun onBindViewHolder(holder: BaseViewHolder, position: Int) {
        val item = modelList[position]
        onBindVHForAll(holder.binding, item)
        ivdManager.onBindVH(holder.binding, item)
        holder.binding.executePendingBindings()
    }

    override fun getItemCount(): Int = modelList.size

    override fun getItemViewType(position: Int): Int = modelList[position].viewType

    override fun onAttachedToRecyclerView(recyclerView: RecyclerView) {
        addDelegate(ivdManager)
        super.onAttachedToRecyclerView(recyclerView)
    }

    override fun onDetachedFromRecyclerView(recyclerView: RecyclerView) {
        ivdManager.clear()
        super.onDetachedFromRecyclerView(recyclerView)
    }

    abstract fun onCreateVHForAll(binding: ViewDataBinding)

    abstract fun onBindVHForAll(binding: ViewDataBinding, model: T)

    /**
     * 初始化各种 viewType 处理委托，添加到 Manager 中
     */
    protected open fun addDelegate(manager: ViewTypeDelegateManager<T>) {}

    protected open fun createVH(parent: ViewGroup, viewType: Int): BaseViewHolder {
        val binding = DataBindingUtil.inflate<ViewDataBinding>(
            LayoutInflater.from(parent.context), viewType, parent, false
        )
        return BaseViewHolder(binding)
    }

    protected fun List<T>.toMultiList(): List<T> {
        return fix?.fix(this) ?: this
    }

    interface IFix<T> {
        fun fix(list: List<T>): List<T>
    }

    class BaseViewHolder(val binding: ViewDataBinding) : RecyclerView.ViewHolder(binding.root)
}