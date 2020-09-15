package io.goooler.demoapp.main.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import io.goooler.demoapp.base.core.BaseFragment
import io.goooler.demoapp.base.util.getViewModel
import io.goooler.demoapp.base.util.unsafeLazy
import io.goooler.demoapp.common.router.RouterManager
import io.goooler.demoapp.main.databinding.MainFragmentBinding
import io.goooler.demoapp.main.vm.MainViewModel

class MainFragment : BaseFragment() {

    private val binding by unsafeLazy { MainFragmentBinding.inflate(layoutInflater) }

    private val vm by getViewModel<MainViewModel>()

    private val initOnce by unsafeLazy {
        binding.lifecycleOwner = this
        binding.vm = vm
        binding.listener = eventListener
        vm.initData()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        initOnce
        return binding.root
    }

    private val eventListener = object : EventListener {
        override fun onTitleClick() {
            RouterManager.goWeb("https://www.github.com/")
        }
    }

    interface EventListener {
        fun onTitleClick()
    }

    companion object {
        fun newInstance() = MainFragment()
    }
}
