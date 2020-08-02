package io.goooler.demoapp.adapter.rv.list

/**
 * Created on 2019/09/30.
 *
 * @author feling
 * @version 1.0.0
 * @since 1.0.0
 */
@Suppress("unused")
interface IMutableListData<M> {

    /**
     * 获取数据。
     */
    fun getData(): MutableList<M>

    /**
     * 设置数据
     */
    fun setData(list: List<M>)

    /**
     * 添加数据
     */
    fun addData(list: List<M>)

    /**
     * 只刷新局部数据。
     */
    fun changeData(list: List<M>)

    /**
     * 只移除局部数据。
     */
    fun removeData(list: List<M>)

    /**
     * 设置数据
     */
    fun setData(vararg ms: M) {
        setData(ms.toList())
    }

    /**
     * 添加数据
     */
    fun addData(vararg ms: M) {
        addData(ms.toList())
    }

    /**
     * 只刷新局部数据。
     */
    fun changeData(vararg ms: M) {
        changeData(ms.toList())
    }

    /**
     * 只移除局部数据。
     */
    fun removeData(vararg ms: M) {
        removeData(ms.toList())
    }

    /**
     * 清空数据
     */
    fun clearData()
}