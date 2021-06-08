package io.goooler.demoapp.main.ui.fragment

import android.annotation.TargetApi
import android.content.Context
import android.content.Intent
import android.os.Build
import android.view.View
import androidx.core.content.pm.ShortcutInfoCompat
import androidx.core.graphics.drawable.IconCompat
import dagger.hilt.android.AndroidEntryPoint
import io.goooler.demoapp.base.util.addDynamicShortcutCompat
import io.goooler.demoapp.base.util.unsafeLazy
import io.goooler.demoapp.common.base.BaseThemeLazyFragment
import io.goooler.demoapp.common.router.RouterManager
import io.goooler.demoapp.common.ui.FullScreenDialogFragment
import io.goooler.demoapp.common.util.getThemeViewModel
import io.goooler.demoapp.main.R
import io.goooler.demoapp.main.databinding.MainHomeFragmentBinding
import io.goooler.demoapp.main.ui.AudioPlayActivity
import io.goooler.demoapp.main.vm.MainHomeViewModel
import java.util.concurrent.CancellationException

@AndroidEntryPoint
class MainHomeFragment : BaseThemeLazyFragment<MainHomeFragmentBinding>() {

  private val vm: MainHomeViewModel by getThemeViewModel()

  private val initData by unsafeLazy {
    vm.initData()
  }

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
    val intent = Intent(context, AudioPlayActivity::class.java)
      .setAction(Intent.ACTION_VIEW)
    val shortcut = ShortcutInfoCompat.Builder(context, SHORTCUT_ID)
      .setShortLabel("Start audio play")
      .setLongLabel("Start audio play")
      .setIcon(IconCompat.createWithResource(context, R.drawable.common_ic_kt_red))
      .setIntent(intent)
      .build()
    context.addDynamicShortcutCompat(SHORTCUT_ID, shortcut)
  }

  companion object {
    private const val SHORTCUT_ID = "audioPlayShortcutId"
  }
}
