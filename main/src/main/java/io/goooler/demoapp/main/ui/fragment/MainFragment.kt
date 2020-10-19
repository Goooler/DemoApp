package io.goooler.demoapp.main.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import io.goooler.demoapp.base.core.BaseLazyFragment
import io.goooler.demoapp.base.util.unsafeLazy
import io.goooler.demoapp.common.router.RouterManager
import io.goooler.demoapp.common.util.getViewModel
import io.goooler.demoapp.main.databinding.MainFragmentBinding
import io.goooler.demoapp.main.vm.MainViewModel

class MainFragment : BaseLazyFragment() {

    private val binding by unsafeLazy { MainFragmentBinding.inflate(layoutInflater) }

    private val vm by getViewModel<MainViewModel>()

    private val initOnce by unsafeLazy {
        binding.lifecycleOwner = viewLifecycleOwner
        binding.vm = vm
        binding.listener = eventListener
    }

    override fun onFragmentResume() {
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

    private val eventListener = View.OnClickListener {
        when (it) {
            binding.btOne -> {
                RouterManager.goWeb("https://www.github.com")
            }
        }
    }

    companion object {
        fun newInstance() = MainFragment()
    }
}
