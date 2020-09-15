@file:Suppress("unused")

package io.goooler.demoapp.adapter.rv.list

import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import io.goooler.demoapp.adapter.rv.base.BaseRvAdapter
import io.goooler.demoapp.adapter.rv.base.IModelType

/**
 * Created on 2019/08/22.
 *
 * @author feling
 * @version 1.0.0
 * @since 1.0.0
 */
abstract class BaseListAdapter<M : IModelType> : BaseRvAdapter<M>(), IMutableListData<M> {

    override val modelList: MutableList<M> = ArrayList()

    override fun onAttachedToRecyclerView(recyclerView: RecyclerView) {
        super.onAttachedToRecyclerView(recyclerView)
        (recyclerView.layoutManager as? GridLayoutManager)?.let {
            it.spanSizeLookup = object : GridLayoutManager.SpanSizeLookup() {
                override fun getSpanSize(position: Int): Int = modelList[position].spanSize
            }
        }
    }

    override fun getData(): MutableList<M> = modelList

    /**
     * 设置数据
     */
    override fun setData(list: List<M>) {
        modelList.run {
            clear()
            addAll(list.toMultiList())
        }
        notifyDataSetChanged()
    }

    /**
     * 添加数据
     */
    override fun addData(list: List<M>) {
        val multiList = list.toMultiList()
        modelList.addAll(multiList)
        notifyItemRangeInserted(list.size, multiList.size)
    }

    /**
     * 只刷新局部数据
     */
    override fun changeData(list: List<M>) {
        modelList.toMultiList().forEach {
            if (it in modelList) {
                notifyItemChanged(modelList.indexOf(it))
            }
        }
    }

    /**
     * 只移除局部数据
     */
    override fun removeData(list: List<M>) {
        list.toMultiList().forEach {
            if (it in modelList) {
                val index = modelList.indexOf(it)
                modelList.remove(it)
                notifyItemRemoved(index)
            }
        }
    }

    /**
     * 清空数据
     */
    override fun clearData() {
        modelList.clear()
    }
}

@Suppress("UNCHECKED_CAST")
@BindingAdapter("binding_rv_setData")
fun RecyclerView.setData(list: List<IModelType>?) {
    if (list == null) return
    (adapter as? BaseListAdapter<IModelType>)?.setData(list)
}