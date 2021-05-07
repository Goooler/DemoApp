package io.goooler.demoapp.main.ui.fragment

import android.view.View
import com.scwang.smart.refresh.layout.api.RefreshLayout
import com.scwang.smart.refresh.layout.listener.OnRefreshLoadMoreListener
import dagger.hilt.android.AndroidEntryPoint
import io.goooler.demoapp.base.util.unsafeLazy
import io.goooler.demoapp.common.base.BaseThemeLazyFragment
import io.goooler.demoapp.common.util.enableRefreshAndLoadMore
import io.goooler.demoapp.common.util.getThemeViewModel
import io.goooler.demoapp.common.util.showToast
import io.goooler.demoapp.main.R
import io.goooler.demoapp.main.databinding.MainSrlFragmentBinding
import io.goooler.demoapp.main.ui.adapter.MainSrlRvAdapter
import io.goooler.demoapp.main.vm.MainSrlViewModel

@AndroidEntryPoint
class MainSrlFragment : BaseThemeLazyFragment<MainSrlFragmentBinding>() {

  private val vm: MainSrlViewModel by getThemeViewModel()

  private val rvAdapter by unsafeLazy {
    MainSrlRvAdapter(listener)
  }

  private val initData by unsafeLazy {
    binding.refreshLayout.autoRefresh()
  }

  override fun onFragmentResume() {
    initData
  }

  override fun initOnce() {
    binding.let {
      it.vm = vm
      it.refreshLayout.setOnRefreshLoadMoreListener(listener)
      it.rvList.adapter = rvAdapter
    }
  }

  private val listener = object :
    MainSrlRvAdapter.OnEventListener,
    OnRefreshLoadMoreListener,
    View.OnClickListener {
    override fun onContentClick(content: String) {
      content.showToast()
    }

    override fun onRefresh(refreshLayout: RefreshLayout) {
      vm.refresh()
    }

    override fun onLoadMore(refreshLayout: RefreshLayout) {
      vm.loadMore()
    }

    override fun onClick(v: View) {
      when (v.id) {
        R.id.iv_error, R.id.tv_tip -> {
          binding.refreshLayout.enableRefreshAndLoadMore()
          binding.refreshLayout.autoRefresh()
        }
      }
    }
  }
}
