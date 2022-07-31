package io.goooler.demoapp.common.ui

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
import androidx.fragment.app.FragmentManager
import io.goooler.demoapp.base.util.putArguments
import io.goooler.demoapp.common.R
import io.goooler.demoapp.common.base.binding.BaseBindingDialogFragment
import io.goooler.demoapp.common.databinding.CommonBottomTipDialogFragmentBinding

class BottomTipDialogFragment : BaseBindingDialogFragment<CommonBottomTipDialogFragmentBinding>() {

  override fun onCreateView(
    inflater: LayoutInflater,
    container: ViewGroup?,
    savedInstanceState: Bundle?
  ): View {
    dialog?.let {
      it.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
      it.requestWindowFeature(Window.FEATURE_NO_TITLE)
    }
    return super.onCreateView(inflater, container, savedInstanceState)
  }

  override fun onResume() {
    super.onResume()
    dialog?.window?.run {
      setWindowAnimations(R.style.CommonDialogBottomAnim)
      attributes = attributes?.apply {
        width = ViewGroup.LayoutParams.MATCH_PARENT
        height = ViewGroup.LayoutParams.MATCH_PARENT
        gravity = Gravity.BOTTOM
      }
    }
  }

  override fun initOnce() {
    binding.let {
      arguments?.let { bundle ->
        it.tvTitle.text = bundle.getString(TITLE)
        it.tvContent.text = bundle.getString(CONTENT)
      }
      it.ivClose.setOnClickListener {
        dismiss()
      }
    }
  }

  companion object {
    private const val TITLE = "title"
    private const val CONTENT = "content"

    fun show(manager: FragmentManager, title: String, content: String, tag: String? = null) {
      BottomTipDialogFragment().putArguments(
        TITLE to title,
        CONTENT to content
      ).show(manager, tag)
    }
  }
}
