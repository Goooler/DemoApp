package io.goooler.demoapp.main.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import io.goooler.demoapp.base.core.BaseLazyFragment
import io.goooler.demoapp.base.util.unsafeLazy
import io.goooler.demoapp.common.util.getViewModel
import io.goooler.demoapp.common.util.showToast
import io.goooler.demoapp.main.databinding.MainPagingFragmentBinding
import io.goooler.demoapp.main.ui.adapter.MainListAdapter
import io.goooler.demoapp.main.vm.MainPagingViewModel

class MainPagingFragment : BaseLazyFragment() {

    private val binding by unsafeLazy { MainPagingFragmentBinding.inflate(layoutInflater) }

    private val vm by getViewModel<MainPagingViewModel>()

    private val listAdapter by unsafeLazy {
        MainListAdapter(listener)
    }

    private val initView by unsafeLazy {
        binding.lifecycleOwner = viewLifecycleOwner
        binding.vm = vm
        binding.rvList.adapter = listAdapter
    }

    private val initData by unsafeLazy {
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

    private val listener = object : MainListAdapter.OnEventListener {
        override fun onContentClick(content: String) {
            showToast(content)
        }
    }

    companion object {
        fun newInstance() = MainPagingFragment()
    }
}
