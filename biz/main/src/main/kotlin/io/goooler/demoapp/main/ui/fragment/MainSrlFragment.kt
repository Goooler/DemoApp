package io.goooler.demoapp.main.ui.fragment

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.ItemTouchHelper
import com.scwang.smart.refresh.layout.api.RefreshLayout
import com.scwang.smart.refresh.layout.listener.OnRefreshLoadMoreListener
import io.goooler.demoapp.adapter.rv.core.ItemTouchHelperCallback
import io.goooler.demoapp.base.util.unsafeLazy
import io.goooler.demoapp.common.base.binding.BaseBindingFragment
import io.goooler.demoapp.common.router.RouterManager
import io.goooler.demoapp.common.util.enableRefreshAndLoadMore
import io.goooler.demoapp.main.databinding.MainSrlFragmentBinding
import io.goooler.demoapp.main.ui.EventFloatingWindow
import io.goooler.demoapp.main.ui.adapter.MainSrlRvAdapter
import io.goooler.demoapp.main.vm.MainSrlViewModel
import kotlinx.coroutines.launch

class MainSrlFragment : BaseBindingFragment<MainSrlFragmentBinding>() {

  private val vm: MainSrlViewModel by viewModels()

  private val rvAdapter by unsafeLazy { MainSrlRvAdapter(listener) }

  private val initData by unsafeLazy { binding.refreshLayout.autoRefresh() }

  override fun initOnce() {
    binding.let {
      it.vm = vm
      it.refreshLayout.setOnRefreshLoadMoreListener(listener)
      it.rvList.adapter = rvAdapter
    }
    ItemTouchHelper(ItemTouchHelperCallback(listener, itemViewSwipeEnabled = true))
      .attachToRecyclerView(binding.rvList)
    EventFloatingWindow.show(requireActivity())
  }

  override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
    super.onViewCreated(view, savedInstanceState)
    lifecycleScope.launch {
      vm.listData.collect {
        EventFloatingWindow.setData(it)
      }
    }
  }

  override fun onResume() {
    super.onResume()
    initData
  }

  private val listener = object :
    MainSrlRvAdapter.OnEventListener,
    OnRefreshLoadMoreListener,
    ItemTouchHelperCallback.ItemChangeListener,
    View.OnClickListener {
    override fun onContentClick(fullName: String) {
      RouterManager.goRepoDetail(requireContext(), fullName)
    }

    override fun onShareClick(fullName: String) {
      vm.share(fullName)
    }

    override fun onRefresh(refreshLayout: RefreshLayout) {
      vm.refresh()
    }

    override fun onLoadMore(refreshLayout: RefreshLayout) {
      vm.loadMore()
    }

    override fun onItemMove(fromPosition: Int, toPosition: Int) {
      vm.swapItems(fromPosition, toPosition)
    }

    override fun onItemDismiss(position: Int) {
      vm.deleteItem(position)
    }

    override fun onClick(v: View) {
      when (v.id) {
        io.goooler.demoapp.common.R.id.iv_error, io.goooler.demoapp.common.R.id.tv_tip -> {
          binding.refreshLayout.enableRefreshAndLoadMore()
          binding.refreshLayout.autoRefresh()
        }
      }
    }
  }
}
