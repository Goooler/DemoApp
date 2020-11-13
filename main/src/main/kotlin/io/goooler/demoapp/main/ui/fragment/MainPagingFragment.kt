package io.goooler.demoapp.main.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.lifecycleScope
import com.scwang.smart.refresh.layout.api.RefreshLayout
import com.scwang.smart.refresh.layout.listener.OnRefreshListener
import io.goooler.demoapp.adapter.rv.paging.BaseRvPagingAdapter
import io.goooler.demoapp.base.core.BaseLazyFragment
import io.goooler.demoapp.base.util.unsafeLazy
import io.goooler.demoapp.common.util.disableRefreshAndLoadMore
import io.goooler.demoapp.common.util.finishRefreshAndLoadMore
import io.goooler.demoapp.common.util.getViewModel
import io.goooler.demoapp.common.util.showToast
import io.goooler.demoapp.main.databinding.MainPagingFragmentBinding
import io.goooler.demoapp.main.ui.adapter.MainPagingRvAdapter
import io.goooler.demoapp.main.vm.MainPagingViewModel
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class MainPagingFragment private constructor() : BaseLazyFragment() {

    private val binding by unsafeLazy { MainPagingFragmentBinding.inflate(layoutInflater) }

    private val vm by getViewModel<MainPagingViewModel>()

    private val listAdapter = MainPagingRvAdapter {
        it.showToast()
    }

    private val initView by unsafeLazy {
        binding.lifecycleOwner = viewLifecycleOwner
        binding.smartRefresh.setOnRefreshListener(listener)
        binding.rvList.adapter = listAdapter
        binding.layoutError.listener = listener
        listAdapter.onLoadStatusListener = listener
    }

    private val initData by unsafeLazy {
        lifecycleScope.launch {
            vm.listData.collectLatest {
                listAdapter.submitData(it)
            }
        }
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

    private val listener = object : View.OnClickListener, BaseRvPagingAdapter.OnLoadStatusListener,
        OnRefreshListener {
        override fun onRefresh(refreshLayout: RefreshLayout) {
            listAdapter.refresh()
        }

        override fun onClick(v: View?) {
            when (v) {
                binding.layoutError.ivError,
                binding.layoutError.tvTip -> {
                    listAdapter.refresh()
                    binding.layoutError.root.visibility = View.GONE
                }
            }
        }

        override fun onNotLoading() {
            binding.smartRefresh.finishRefreshAndLoadMore()
        }

        override fun onNoMoreData() {
            binding.smartRefresh.finishLoadMoreWithNoMoreData()
        }

        override fun onEmpty() {
            binding.smartRefresh.disableRefreshAndLoadMore()
            binding.layoutEmpty.root.visibility = View.VISIBLE
        }

        override fun onError(t: Throwable) {
            binding.layoutError.root.visibility = View.VISIBLE
        }
    }

    companion object {
        fun newInstance() = MainPagingFragment()
    }
}
