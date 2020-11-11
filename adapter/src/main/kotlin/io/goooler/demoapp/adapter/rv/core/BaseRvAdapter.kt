package io.goooler.demoapp.adapter.rv.core

import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.recyclerview.widget.RecyclerView

/**
 * Created on 2020/10/22.
 *
 * Simple FeAdapter. You can write your own {@link RecyclerView.Adapter} according to this.
 *
 * @author feling
 * @version 1.0.0
 * @since 1.0.0
 * @see RecyclerView.Adapter
 * @see RvAdapterHelper
 * @see IRvAdapter
 */
abstract class BaseRvAdapter<M : IVhModelType> : RecyclerView.Adapter<BindingViewHolder>(),
    IRvAdapter<M>, IRvAdapterMutable<M> {

    private val helper by lazy(LazyThreadSafetyMode.NONE) { RvAdapterHelper(this) }

    private val dataList = ArrayList<M>()

    override fun onAttachedToRecyclerView(recyclerView: RecyclerView) {
        super.onAttachedToRecyclerView(recyclerView)
        helper.onAttachedToRecyclerView(recyclerView)
    }

    override fun onDetachedFromRecyclerView(recyclerView: RecyclerView) {
        super.onDetachedFromRecyclerView(recyclerView)
        helper.onDetachedFromRecyclerView(recyclerView)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BindingViewHolder {
        return helper.onCreateViewHolder(parent, viewType)
    }

    override fun onBindViewHolder(holder: BindingViewHolder, position: Int) {
        helper.onBindViewHolder(holder, position)
    }

    @LayoutRes
    override fun getItemViewType(position: Int): Int = dataList[position].viewType

    override fun getItemCount(): Int = dataList.size

    override fun setList(list: List<M>) {
        dataList.run {
            clear()
            addAll(helper.transform(list))
        }
        notifyDataSetChanged()
    }

    override fun getList(): List<M> = dataList

    override fun getModel(position: Int): M? = dataList[position]

    override fun refreshItems(items: List<M>) {
        helper.refreshItems(items, dataList) {
            if (it in 0 until itemCount) {
                notifyItemChanged(it)
            }
        }
    }
}