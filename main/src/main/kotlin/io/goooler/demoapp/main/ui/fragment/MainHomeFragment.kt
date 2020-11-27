package io.goooler.demoapp.main.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import io.goooler.demoapp.base.util.unsafeLazy
import io.goooler.demoapp.common.base.BaseThemeLazyFragment
import io.goooler.demoapp.common.router.RouterManager
import io.goooler.demoapp.common.util.getViewModel
import io.goooler.demoapp.main.R
import io.goooler.demoapp.main.databinding.MainHomeFragmentBinding
import io.goooler.demoapp.main.vm.MainHomeViewModel
import java.util.concurrent.CancellationException

class MainHomeFragment private constructor() : BaseThemeLazyFragment() {

    private val binding by unsafeLazy {
        MainHomeFragmentBinding.inflate(layoutInflater).also {
            it.lifecycleOwner = viewLifecycleOwner
            it.vm = vm
            it.listener = eventListener
        }
    }

    private val vm: MainHomeViewModel by getViewModel()

    override fun onFragmentResume() {
        vm.initData()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return binding.root
    }

    private val eventListener = View.OnClickListener {
        when (it.id) {
            R.id.bt_one -> {
                RouterManager.goWeb("http://m.bilibili.com")
            }
            R.id.bt_two -> {
                vm.getRepoListFromDb()
            }
            R.id.bt_three -> {
                if (vm.countdownJob?.isActive != true) {
                    vm.startCountDown {

                    }
                } else {
                    vm.countdownJob?.cancel(CancellationException(MainHomeViewModel.CANCEL_MANUALLY))
                }
            }
        }
    }

    companion object {
        fun newInstance() = MainHomeFragment()
    }
}
