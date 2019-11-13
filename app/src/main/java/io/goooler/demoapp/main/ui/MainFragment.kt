package io.goooler.demoapp.main.ui

import android.view.View
import io.goooler.demoapp.R
import io.goooler.demoapp.base.BaseLazyFragment
import io.goooler.demoapp.databinding.FragmentMainBinding
import io.goooler.demoapp.main.vm.MainViewModel

class MainFragment : BaseLazyFragment() {

    private val binding by lazy {
        inflate<FragmentMainBinding>(R.layout.fragment_main)
    }

    private val vm by lazy {
        getViewModel(MainViewModel::class.java)
    }

    private val initOnce by lazy {
        binding.vm = vm
    }

    override fun initView(): View {
        return binding.root
    }

    override fun loadData() {
        initOnce
    }

    companion object {
        fun newInstance() = MainFragment()
    }
}
