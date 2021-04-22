package io.goooler.demoapp.main.ui.fragment

import android.annotation.TargetApi
import android.content.Context
import android.content.Intent
import android.content.pm.ShortcutInfo
import android.content.pm.ShortcutManager
import android.graphics.drawable.Icon
import android.os.Build
import android.view.View
import androidx.core.content.getSystemService
import dagger.hilt.android.AndroidEntryPoint
import io.goooler.demoapp.base.util.unsafeLazy
import io.goooler.demoapp.common.base.BaseThemeLazyFragment
import io.goooler.demoapp.common.router.RouterManager
import io.goooler.demoapp.common.ui.FullScreenDialogFragment
import io.goooler.demoapp.common.util.getViewModel
import io.goooler.demoapp.main.R
import io.goooler.demoapp.main.databinding.MainHomeFragmentBinding
import io.goooler.demoapp.main.ui.AudioPlayActivity
import io.goooler.demoapp.main.vm.MainHomeViewModel
import java.util.concurrent.CancellationException

@AndroidEntryPoint
class MainHomeFragment :
  BaseThemeLazyFragment<MainHomeFragmentBinding>(R.layout.main_home_fragment) {

  private val vm: MainHomeViewModel by getViewModel()

  private val initData by unsafeLazy(vm::initData)

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
      binding.bt8 -> createShortcut(requireContext())
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

  @TargetApi(Build.VERSION_CODES.N_MR1)
  private fun createShortcut(context: Context) {
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N_MR1) {
      context.getSystemService<ShortcutManager>()?.let { sm ->
        if (sm.dynamicShortcuts.any { it.id == SHORTCUT_ID }) {
          return
        }
        val intent = Intent(context, AudioPlayActivity::class.java)
          .setAction(Intent.ACTION_VIEW)
        val shortcut = ShortcutInfo.Builder(context, SHORTCUT_ID)
          .setShortLabel("Start audio play")
          .setLongLabel("Start audio play")
          .setIcon(Icon.createWithResource(context, R.drawable.common_ic_kt_red))
          .setIntent(intent)
          .build()
        try {
          sm.addDynamicShortcuts(listOf(shortcut))
        } catch (_: Exception) {
        }
      }
    }
  }

  companion object {
    private const val SHORTCUT_ID = "audioPlayShortcutId"
  }
}
