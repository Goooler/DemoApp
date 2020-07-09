package io.goooler.demoapp.adapter.rv.list

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

    private val items = ArrayList<M>()

    override val list: List<M>
        get() = items

    override fun getData(): MutableList<M> {
        return items
    }

    /**
     * 设置数据
     */
    override fun setData(list: List<M>) {
        items.run {
            clear()
            addAll(list.multiList())
        }
        notifyDataSetChanged()
    }

    /**
     * 添加数据
     */
    override fun addData(list: List<M>) {
        val multiList = list.multiList()
        items.addAll(multiList)
        notifyItemRangeInserted(items.size, multiList.size)
    }

    /**
     * 只刷新局部数据。
     */
    override fun changeData(list: List<M>) {
        list.multiList().forEach {
            if (it in items) {
                val index = items.indexOf(it)
                notifyItemChanged(index)
            }
        }
    }

    /**
     * 只移除局部数据。
     */
    override fun removeData(list: List<M>) {
        list.multiList().forEach {
            if (it in items) {
                val index = items.indexOf(it)
                items.remove(it)
                notifyItemRemoved(index)
            }
        }
    }

    /**
     * 清空数据
     */
    override fun clearData() {
        items.clear()
    }
}