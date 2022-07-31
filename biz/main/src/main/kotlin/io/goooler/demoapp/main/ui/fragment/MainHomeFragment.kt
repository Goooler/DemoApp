package io.goooler.demoapp.main.ui.fragment

import android.annotation.TargetApi
import android.content.Context
import android.content.Intent
import android.os.Build
import android.view.View
import androidx.core.content.pm.ShortcutInfoCompat
import androidx.core.graphics.drawable.IconCompat
import androidx.fragment.app.viewModels
import dagger.hilt.android.AndroidEntryPoint
import io.goooler.demoapp.base.util.addDynamicShortcutCompat
import io.goooler.demoapp.base.util.unsafeLazy
import io.goooler.demoapp.common.base.binding.BaseBindingFragment
import io.goooler.demoapp.common.router.RouterManager
import io.goooler.demoapp.common.ui.BottomTipDialogFragment
import io.goooler.demoapp.main.databinding.MainHomeFragmentBinding
import io.goooler.demoapp.main.ui.AudioPlayActivity
import io.goooler.demoapp.main.vm.MainHomeViewModel

@AndroidEntryPoint
class MainHomeFragment : BaseBindingFragment<MainHomeFragmentBinding>() {

  private val vm: MainHomeViewModel by viewModels()

  private val initData by unsafeLazy { vm.initData() }

  override fun initOnce() {
    binding.let {
      it.vm = vm
      it.listener = listener
    }
  }

  override fun onResume() {
    super.onResume()
    initData
  }

  private val listener = View.OnClickListener {
    when (it) {
      binding.bt1 -> RouterManager.goWeb(requireContext(), "bilibili.com")
      binding.bt4 -> vm.countDown()
      binding.bt6 -> BottomTipDialogFragment.show(childFragmentManager, "bottom", "IT之家了解到，这款手表的表壳、表带、表圈等采用不锈钢打造，表壳采用抗冲击结构，支持 20 个大气压防水；搭载了太阳能充电系统，还支持通过蓝牙与智能手机连接，从而实现显示准确的时间，还可以通过智能手机应用程序轻松设置闹钟、定时器、世界时间等。")
      binding.bt7 -> RouterManager.goAudioPlay(requireContext())
      binding.bt8 -> if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N_MR1) {
        createShortcut(requireContext())
      }
    }
  }

  @TargetApi(Build.VERSION_CODES.N_MR1)
  private fun createShortcut(context: Context) {
    val intent = Intent(context, AudioPlayActivity::class.java)
      .setAction(Intent.ACTION_VIEW)
    val shortcut = ShortcutInfoCompat.Builder(context, SHORTCUT_ID)
      .setShortLabel("Start audio play")
      .setLongLabel("Start audio play")
      .setIcon(
        IconCompat.createWithResource(
          context,
          io.goooler.demoapp.common.R.drawable.common_ic_kt_red
        )
      )
      .setIntent(intent)
      .build()
    context.addDynamicShortcutCompat(SHORTCUT_ID, shortcut)
  }

  companion object {
    private const val SHORTCUT_ID = "audioPlayShortcutId"
  }
}
