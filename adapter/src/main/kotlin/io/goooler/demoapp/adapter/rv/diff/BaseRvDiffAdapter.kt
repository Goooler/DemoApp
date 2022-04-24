package io.goooler.demoapp.adapter.rv.diff

import android.os.Parcel
import android.os.Parcelable
import android.view.ViewGroup
import androidx.annotation.IntRange
import androidx.annotation.LayoutRes
import androidx.recyclerview.widget.AsyncDifferConfig
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import io.goooler.demoapp.adapter.rv.core.BindingViewHolder
import io.goooler.demoapp.adapter.rv.core.IMutableRvAdapter
import io.goooler.demoapp.adapter.rv.core.RvAdapterHelper
import java.util.Collections

/**
 * Created on 2020/10/22.
 *
 * DiffListAdapter. This adapter used [DiffUtil].
 *
 * @author feling
 * @version 1.0.0
 * @since 1.0.0
 */
abstract class BaseRvDiffAdapter<M> :
  ListAdapter<M, BindingViewHolder>,
  IMutableRvAdapter<M> where M : IDiffVhModelType, M : Parcelable {

  private val helper by lazy(LazyThreadSafetyMode.NONE) { RvAdapterHelper(this) }

  constructor(callback: DiffCallBack<M> = DiffCallBack()) : super(callback)

  constructor(config: AsyncDifferConfig<M>) : super(config)

  override fun onAttachedToRecyclerView(recyclerView: RecyclerView) {
    super.onAttachedToRecyclerView(recyclerView)
    helper.onAttachedToRecyclerView(recyclerView)
  }

  override fun onDetachedFromRecyclerView(recyclerView: RecyclerView) {
    super.onDetachedFromRecyclerView(recyclerView)
    helper.onDetachedFromRecyclerView(recyclerView)
  }

  override fun onCreateViewHolder(
    parent: ViewGroup,
    @LayoutRes viewType: Int
  ): BindingViewHolder = helper.onCreateViewHolder(parent, viewType)

  override fun onBindViewHolder(
    holder: BindingViewHolder,
    @IntRange(from = 0) position: Int
  ) {
    helper.onBindViewHolder(holder, position)
  }

  @LayoutRes
  override fun getItemViewType(@IntRange(from = 0) position: Int): Int =
    getItem(position).viewType

  override operator fun get(@IntRange(from = 0) position: Int): M = getItem(position)

  override var list: List<M>
    get() = Collections.unmodifiableList(helper.list)
    set(value) {
      helper.list = value.map {
        val parcel = Parcel.obtain()
        it.writeToParcel(parcel, 0)
        parcel.setDataPosition(0)
        it.javaClass.getDeclaredConstructor(Parcel::class.java).apply {
          isAccessible = true
        }.newInstance(parcel)
      }
      submitList(helper.transform(helper.list))
    }

  /**
   * Please do not use it with setList() !
   */
  override fun refreshItems(items: List<M>) {
    helper.refreshItems(items) {
      if (it in 0 until itemCount) {
        notifyItemChanged(it)
      }
    }
  }

  override fun removeItem(index: Int) {
    helper.removeItem(index)
    notifyItemRemoved(index)
  }

  override fun removeItem(item: M) {
    helper.removeItem(item) {
      notifyItemRemoved(it)
    }
  }
}
