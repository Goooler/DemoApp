package io.goooler.demoapp.adapter.rv.base

import androidx.databinding.ViewDataBinding

/**
 * Created on 2019/08/30.
 * 同一种 ViewType 的每个 Item 对 View 的处理委托（接口），一种 viewType 一个对象即可
 *
 * @author feling
 * @version 1.0.0
 * @since 1.0.0
 */
interface ViewTypeDelegate<DB : ViewDataBinding, M : IModelType> {

    /**
     * viewType 满足某添加后委托生效，使用时写跟 M 的 getViewType() 值相同
     * @return viewType
     */
    val viewType: Int

    /**
     * 当创建 ViewHolder 时做什么事
     * @param binding ViewDataBinding
     */
    fun onCreateVH(binding: DB)

    /**
     * 当 Bind ViewHolder 时做什么事
     * @param binding ViewDataBinding
     * @param m 具体的 Model
     */
    fun onBindVH(binding: DB, m: M)
}