package io.goooler.demoapp.main.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.blankj.utilcode.util.BarUtils
import com.scwang.smart.refresh.layout.api.RefreshLayout
import com.scwang.smart.refresh.layout.listener.OnRefreshLoadMoreListener
import io.goooler.demoapp.base.core.BaseFragment
import io.goooler.demoapp.base.util.unsafeLazy
import io.goooler.demoapp.common.util.getViewModel
import io.goooler.demoapp.common.util.showToast
import io.goooler.demoapp.main.databinding.MainListFragmentBinding
import io.goooler.demoapp.main.ui.adapter.MainListAdapter
import io.goooler.demoapp.main.vm.MainListViewModel

class MainListFragment : BaseFragment() {

    private val binding by unsafeLazy { MainListFragmentBinding.inflate(layoutInflater) }

    private val vm by getViewModel<MainListViewModel>()

    private val adapter by unsafeLazy {
        MainListAdapter(listener)
    }

    private val initOnce by unsafeLazy {
        BarUtils.setStatusBarLightMode(requireActivity(), true)
        binding.lifecycleOwner = viewLifecycleOwner
        binding.vm = vm
        binding.smartRefresh.setOnRefreshLoadMoreListener(listener)
        binding.rvList.adapter = adapter
        vm.listData.observe(viewLifecycleOwner) {
            adapter.setData(it)
        }
        binding.smartRefresh.autoRefresh()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        initOnce
        return binding.root
    }

    private val listener = object : MainListAdapter.OnEventListener, OnRefreshLoadMoreListener {
        override fun onContentClick(content: String) {
            showToast(content)
        }

        override fun onRefresh(refreshLayout: RefreshLayout) {
            vm.refresh()
        }

        override fun onLoadMore(refreshLayout: RefreshLayout) {
            vm.loadMore()
        }
    }
}
