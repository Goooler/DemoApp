package io.goooler.demoapp.main.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.lifecycleScope
import com.scwang.smart.refresh.layout.api.RefreshLayout
import com.scwang.smart.refresh.layout.listener.OnRefreshListener
import dagger.hilt.android.AndroidEntryPoint
import io.goooler.demoapp.adapter.rv.paging.BaseRvPagingAdapter
import io.goooler.demoapp.base.util.unsafeLazy
import io.goooler.demoapp.common.base.BaseThemeLazyFragment
import io.goooler.demoapp.common.util.disableRefreshAndLoadMore
import io.goooler.demoapp.common.util.finishRefreshAndLoadMore
import io.goooler.demoapp.common.util.getViewModel
import io.goooler.demoapp.common.util.showToast
import io.goooler.demoapp.main.databinding.MainPagingFragmentBinding
import io.goooler.demoapp.main.ui.adapter.MainPagingRvAdapter
import io.goooler.demoapp.main.vm.MainPagingViewModel
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MainPagingFragment private constructor() : BaseThemeLazyFragment() {

    private lateinit var binding: MainPagingFragmentBinding
    private lateinit var rvAdapter: MainPagingRvAdapter

    private val vm: MainPagingViewModel by getViewModel()

    private val initView by unsafeLazy {
        binding = MainPagingFragmentBinding.inflate(layoutInflater).also {
            it.lifecycleOwner = viewLifecycleOwner
            it.smartRefresh.setOnRefreshListener(listener)
            it.listener = listener
        }
        rvAdapter = MainPagingRvAdapter(listener).apply {
            onLoadStatusListener = listener
        }
        binding.rvList.adapter = rvAdapter
    }

    private val initData by unsafeLazy {
        lifecycleScope.launch {
            vm.listData.collectLatest {
                rvAdapter.submitData(it)
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
    ): View {
        initView
        return binding.root
    }

    private val listener = object :
        View.OnClickListener,
        BaseRvPagingAdapter.OnLoadStatusListener,
        OnRefreshListener,
        MainPagingRvAdapter.OnEventListener {
        override fun onRefresh(refreshLayout: RefreshLayout) {
            rvAdapter.refresh()
        }

        override fun onClick(v: View) {
            when (v) {
                binding.layoutError.ivError,
                binding.layoutError.tvTip -> {
                    rvAdapter.refresh()
                    binding.layoutError.root.visibility = View.GONE
                }
                binding.fabRemove -> {

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

        override fun onContentClick(content: String) {
            content.showToast()
        }
    }

    companion object {
        fun newInstance() = MainPagingFragment()
    }
}
