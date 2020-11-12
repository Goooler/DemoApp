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
import io.goooler.demoapp.main.databinding.MainSrlFragmentBinding
import io.goooler.demoapp.main.ui.adapter.MainSrlRvAdapter
import io.goooler.demoapp.main.vm.MainSrlModel

class MainSrlFragment private constructor() : BaseLazyFragment() {

    private val binding by unsafeLazy { MainSrlFragmentBinding.inflate(layoutInflater) }

    private val vm by getViewModel<MainSrlModel>()

    private val listAdapter by unsafeLazy {
        MainSrlRvAdapter(listener)
    }

    private val initView by unsafeLazy {
        binding.lifecycleOwner = viewLifecycleOwner
        binding.vm = vm
        binding.smartRefresh.setOnRefreshLoadMoreListener(listener)
        binding.rvList.adapter = listAdapter
        vm.listData.observe(viewLifecycleOwner) {
            listAdapter.list = it
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
        fun newInstance() = MainSrlFragment()
    }
}
