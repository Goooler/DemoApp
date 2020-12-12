package io.goooler.demoapp.main.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
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
class MainSrlFragment private constructor() : BaseThemeLazyFragment() {

  private val binding by unsafeLazy {
    MainSrlFragmentBinding.inflate(layoutInflater).also {
      it.lifecycleOwner = viewLifecycleOwner
      it.vm = vm
      it.smartRefresh.setOnRefreshLoadMoreListener(listener)
      it.rvList.adapter = rvAdapter
    }
  }

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

  override fun onCreateView(
    inflater: LayoutInflater,
    container: ViewGroup?,
    savedInstanceState: Bundle?
  ): View = binding.root

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

  companion object {
    fun newInstance(): MainSrlFragment = MainSrlFragment()
  }
}
