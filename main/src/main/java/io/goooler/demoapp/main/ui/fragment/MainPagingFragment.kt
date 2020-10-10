package io.goooler.demoapp.main.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.lifecycleScope
import androidx.paging.LoadState
import io.goooler.demoapp.base.core.BaseLazyFragment
import io.goooler.demoapp.base.util.unsafeLazy
import io.goooler.demoapp.common.util.finishRefreshAndLoadMore
import io.goooler.demoapp.common.util.getViewModel
import io.goooler.demoapp.common.util.showToast
import io.goooler.demoapp.main.databinding.MainPagingFragmentBinding
import io.goooler.demoapp.main.ui.adapter.MainListPagingAdapter
import io.goooler.demoapp.main.vm.MainPagingViewModel
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class MainPagingFragment : BaseLazyFragment() {

    private val binding by unsafeLazy { MainPagingFragmentBinding.inflate(layoutInflater) }

    private val vm by getViewModel<MainPagingViewModel>()

    private val listAdapter by unsafeLazy {
        MainListPagingAdapter {
            showToast(it)
        }
    }

    private val initView by unsafeLazy {
        binding.lifecycleOwner = viewLifecycleOwner
        binding.rvList.adapter = listAdapter
        listAdapter.addLoadStateListener {
            if (it.refresh !is LoadState.Loading) {
                binding.smartRefresh.finishRefreshAndLoadMore()
                if (it.refresh is LoadState.Error) {
                    //todo
                }
            }
        }
        binding.smartRefresh.setOnRefreshListener {
            listAdapter.refresh()
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

    companion object {
        fun newInstance() = MainPagingFragment()
    }
}
