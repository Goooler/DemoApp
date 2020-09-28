package io.goooler.demoapp.main.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.scwang.smart.refresh.layout.api.RefreshLayout
import com.scwang.smart.refresh.layout.listener.OnRefreshLoadMoreListener
import io.goooler.demoapp.base.core.BaseLazyFragment
import io.goooler.demoapp.base.util.unsafeLazy
import io.goooler.demoapp.common.util.getViewModel
import io.goooler.demoapp.common.util.showToast
import io.goooler.demoapp.main.databinding.MainListFragmentBinding
import io.goooler.demoapp.main.ui.adapter.MainListAdapter
import io.goooler.demoapp.main.vm.MainListViewModel

class MainListFragment : BaseLazyFragment() {

    private val binding by unsafeLazy { MainListFragmentBinding.inflate(layoutInflater) }

    private val vm by getViewModel<MainListViewModel>()

    private val adapter by unsafeLazy {
        MainListAdapter(listener)
    }

    private val initView by unsafeLazy {
        binding.lifecycleOwner = viewLifecycleOwner
        binding.vm = vm
        binding.smartRefresh.setOnRefreshLoadMoreListener(listener)
        binding.rvList.adapter = adapter
        vm.listData.observe(viewLifecycleOwner) {
            adapter.setData(it)
        }
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
    ): View? {
        initView
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

    companion object {
        fun newInstance() = MainListFragment()
    }
}
