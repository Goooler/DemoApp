package io.goooler.demoapp.main.ui.fragment

import android.view.View
import dagger.hilt.android.AndroidEntryPoint
import io.goooler.demoapp.base.util.unsafeLazy
import io.goooler.demoapp.common.base.BaseThemeLazyFragment
import io.goooler.demoapp.common.router.RouterManager
import io.goooler.demoapp.common.ui.FullScreenDialogFragment
import io.goooler.demoapp.common.util.getViewModel
import io.goooler.demoapp.main.R
import io.goooler.demoapp.main.databinding.MainHomeFragmentBinding
import io.goooler.demoapp.main.vm.MainHomeViewModel
import java.util.concurrent.CancellationException

@AndroidEntryPoint
class MainHomeFragment :
  BaseThemeLazyFragment<MainHomeFragmentBinding>(R.layout.main_home_fragment) {

  private val vm: MainHomeViewModel by getViewModel()

  private val initData by unsafeLazy { vm.initData() }

  override fun onFragmentResume() {
    initData
  }

  override fun initOnce() {
    binding.let {
      it.vm = vm
      it.listener = listener
    }
  }

  private val listener = View.OnClickListener {
    when (it) {
      binding.bt1 -> RouterManager.goWeb("bilibili.com")
      binding.bt2 -> RouterManager.goMap()
      binding.bt3 -> RouterManager.goCompose()
      binding.bt4 -> countdown()
      binding.bt5 -> RouterManager.goWidget()
      binding.bt6 -> FullScreenDialogFragment.show(childFragmentManager)
      binding.bt7 -> RouterManager.goAudioPlay()
    }
  }

  private fun countdown() {
    if (vm.countdownJob?.isActive != true) {
      vm.startCountDown()
    } else {
      vm.countdownJob?.cancel(
        CancellationException(MainHomeViewModel.CANCEL_MANUALLY)
      )
    }
  }

  companion object {
    fun newInstance(): MainHomeFragment = MainHomeFragment()
  }
}
