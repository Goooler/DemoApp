package io.goooler.demoapp.main.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import io.goooler.demoapp.R
import io.goooler.demoapp.base.BaseFragment
import io.goooler.demoapp.databinding.FragmentMainBinding
import io.goooler.demoapp.main.vm.MainViewModel

class MainFragment : BaseFragment() {

    private val binding by lazy {
        DataBindingUtil.inflate<FragmentMainBinding>(this.layoutInflater, R.layout.fragment_main, null, false)
    }

    private val vm by lazy {
        getViewModel(MainViewModel::class.java)
    }

    private val initOnce by lazy {
        binding.vm = vm
    }

    override fun initView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        return binding.root
    }

    override fun loadData() {
        initOnce
    }

    companion object {
        fun newInstance() = MainFragment()
    }
}
