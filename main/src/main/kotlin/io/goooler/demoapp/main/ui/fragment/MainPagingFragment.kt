package io.goooler.demoapp.main.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.lifecycleScope
import androidx.paging.LoadState
import com.scwang.smart.refresh.layout.api.RefreshLayout
import com.scwang.smart.refresh.layout.listener.OnRefreshListener
import io.goooler.demoapp.adapter.rv.datasource.BasePagingSource
import io.goooler.demoapp.base.core.BaseLazyFragment
import io.goooler.demoapp.base.util.unsafeLazy
import io.goooler.demoapp.common.util.disableRefreshAndLoadMore
import io.goooler.demoapp.common.util.finishRefreshAndLoadMore
import io.goooler.demoapp.common.util.getViewModel
import io.goooler.demoapp.common.util.showToast
import io.goooler.demoapp.main.databinding.MainPagingFragmentBinding
import io.goooler.demoapp.main.ui.adapter.MainListPagingAdapter
import io.goooler.demoapp.main.vm.MainPagingViewModel
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class MainPagingFragment private constructor() : BaseLazyFragment() {

    private val binding by unsafeLazy { MainPagingFragmentBinding.inflate(layoutInflater) }

    private val vm by getViewModel<MainPagingViewModel>()

    private val listAdapter = MainListPagingAdapter {
        it.showToast()
    }

    private val initView by unsafeLazy {
        binding.lifecycleOwner = viewLifecycleOwner
        binding.smartRefresh.setOnRefreshListener(listener)
        binding.rvList.adapter = listAdapter
        binding.layoutError.listener = listener

        listAdapter.addLoadStateListener {
            if (it.refresh !is LoadState.Loading) {
                binding.smartRefresh.finishRefreshAndLoadMore()
                if (it.refresh is LoadState.Error) {
                    when ((it.refresh as LoadState.Error).error) {
                        is BasePagingSource.EmptyDataException -> {
                            binding.smartRefresh.disableRefreshAndLoadMore()
                            binding.layoutEmpty.root.visibility = View.VISIBLE
                        }
                        else -> binding.layoutError.root.visibility = View.VISIBLE
                    }
                }
            }
            when ((it.append as? LoadState.Error)?.error) {
                is BasePagingSource.NoMoreDataException -> {
                    binding.smartRefresh.finishLoadMoreWithNoMoreData()
                }
            }
        }
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

    private val listener = object : OnRefreshListener, View.OnClickListener {
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
    }

    companion object {
        fun newInstance() = MainPagingFragment()
    }
}
