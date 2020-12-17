package io.goooler.demoapp.main.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import dagger.hilt.android.AndroidEntryPoint
import io.goooler.demoapp.common.base.BaseThemeLazyFragment
import io.goooler.demoapp.common.router.RouterManager
import io.goooler.demoapp.common.util.getViewModel
import io.goooler.demoapp.main.R
import io.goooler.demoapp.main.databinding.MainHomeFragmentBinding
import io.goooler.demoapp.main.vm.MainHomeViewModel
import java.util.concurrent.CancellationException

@AndroidEntryPoint
class MainHomeFragment private constructor() :
  BaseThemeLazyFragment<MainHomeFragmentBinding>(R.layout.main_home_fragment) {

  private val vm: MainHomeViewModel by getViewModel()

  override fun onFragmentResume() {
    vm.initData()
  }

  override fun onCreateView(
    inflater: LayoutInflater,
    container: ViewGroup?,
    savedInstanceState: Bundle?
  ): View = binding.also {
    it.vm = vm
    it.listener = listener
  }.root

  private val listener = View.OnClickListener {
    when (it) {
      binding.btOne -> {
        RouterManager.goWeb("http://m.bilibili.com")
      }
      binding.btTwo -> {
        vm.getRepoListFromDb()
      }
      binding.btThree -> {
        vm.getRepoListFromDs()
      }
      binding.btFour -> {
        if (vm.countdownJob?.isActive != true) {
          vm.startCountDown {}
        } else {
          vm.countdownJob?.cancel(
            CancellationException(MainHomeViewModel.CANCEL_MANUALLY)
          )
        }
      }
    }
  }

  companion object {
    fun newInstance(): MainHomeFragment = MainHomeFragment()
  }
}
