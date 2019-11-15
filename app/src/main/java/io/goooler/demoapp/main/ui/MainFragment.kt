package io.goooler.demoapp.main.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import io.goooler.demoapp.R
import io.goooler.demoapp.base.BaseFragment
import io.goooler.demoapp.databinding.FragmentMainBinding
import io.goooler.demoapp.main.vm.MainViewModel

class MainFragment : BaseFragment() {

    private val binding by lazy {
        inflate<FragmentMainBinding>(R.layout.fragment_main)
    }

    private val vm by lazy {
        getViewModel(MainViewModel::class.java)
    }

    private val initOnce by lazy {
        binding.vm = vm
        binding.clickListener = clickListener
        vm.initData()
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        initOnce
    }

    private val clickListener = View.OnClickListener {
        when (it) {
            binding.tvTitle -> {
                showToast(R.string.main_fragment)
            }
        }
    }

    companion object {
        fun newInstance() = MainFragment()
    }
}
