package io.goooler.demoapp.main.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import io.goooler.demoapp.base.BaseFragment
import io.goooler.demoapp.databinding.FragmentMainBinding
import io.goooler.demoapp.main.vm.MainViewModel
import io.goooler.demoapp.widget.ui.BottomTipDialogFragment

class MainFragment : BaseFragment() {

    private val binding by lazy(LazyThreadSafetyMode.NONE) { FragmentMainBinding.inflate(layoutInflater) }

    private val vm by lazy(LazyThreadSafetyMode.NONE) { getViewModel(MainViewModel::class.java) }

    private val initOnce by lazy(LazyThreadSafetyMode.NONE) {
        binding.lifecycleOwner = this@MainFragment
        binding.vm = vm
        binding.listener = eventListener
        vm.initData()
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        initOnce
        return binding.root
    }

    private val eventListener = object : EventListener {
        override fun onTitleClick() {
            BottomTipDialogFragment.show(childFragmentManager, "first", "first content")
        }
    }

    interface EventListener {
        fun onTitleClick()
    }

    companion object {
        fun newInstance() = MainFragment()
    }
}
