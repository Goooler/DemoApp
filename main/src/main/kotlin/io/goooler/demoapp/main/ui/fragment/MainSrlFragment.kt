package io.goooler.demoapp.main.ui.fragment

import com.scwang.smart.refresh.layout.api.RefreshLayout
import com.scwang.smart.refresh.layout.listener.OnRefreshLoadMoreListener
import dagger.hilt.android.AndroidEntryPoint
import io.goooler.demoapp.base.util.unsafeLazy
import io.goooler.demoapp.common.base.BaseThemeLazyFragment
import io.goooler.demoapp.common.util.getViewModel
import io.goooler.demoapp.common.util.showToast
import io.goooler.demoapp.main.databinding.MainSrlFragmentBinding
import io.goooler.demoapp.main.ui.adapter.MainSrlRvAdapter
import io.goooler.demoapp.main.vm.MainSrlViewModel

@AndroidEntryPoint
class MainSrlFragment : BaseThemeLazyFragment<MainSrlFragmentBinding>() {

  private val vm: MainSrlViewModel by getViewModel()

  private val rvAdapter by unsafeLazy {
    MainSrlRvAdapter(listener)
  }

  private val initData by unsafeLazy {
    binding.smartRefresh.autoRefresh()
  }

  override fun onFragmentResume() {
    initData
  }

  override fun initOnce() {
    binding.let {
      it.vm = vm
      it.smartRefresh.setOnRefreshLoadMoreListener(listener)
      it.rvList.adapter = rvAdapter
    }
  }

  private val listener = object : MainSrlRvAdapter.OnEventListener, OnRefreshLoadMoreListener {
    override fun onContentClick(content: String) {
      content.showToast()
    }

    override fun onRefresh(refreshLayout: RefreshLayout) {
      vm.refresh()
    }

    override fun onLoadMore(refreshLayout: RefreshLayout) {
      vm.loadMore()
    }
  }
}
