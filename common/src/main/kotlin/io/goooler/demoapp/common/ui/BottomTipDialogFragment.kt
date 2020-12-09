package io.goooler.demoapp.common.ui

import android.os.Bundle
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.FragmentManager
import io.goooler.demoapp.base.R
import io.goooler.demoapp.base.util.putArguments
import io.goooler.demoapp.base.util.unsafeLazy
import io.goooler.demoapp.common.base.BaseThemeDialogFragment
import io.goooler.demoapp.common.databinding.BottomTipDialogFragmentBinding

class BottomTipDialogFragment private constructor() : BaseThemeDialogFragment() {

    private val binding by unsafeLazy {
        BottomTipDialogFragmentBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setStyle(STYLE_NORMAL, R.style.DialogTransparentTheme)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding.lifecycleOwner = this
        arguments?.let {
            binding.tvTitle.text = it.getString(TITLE)
            binding.tvContent.text = it.getString(CONTENT)
        }
        binding.ivClose.setOnClickListener {
            dismiss()
        }
        return binding.root
    }

    override fun onResume() {
        super.onResume()
        setStyle()
    }

    private fun setStyle() {
        dialog?.window?.run {
            setWindowAnimations(R.style.DialogBottomAnim)
            attributes = attributes?.apply {
                width = ViewGroup.LayoutParams.MATCH_PARENT
                height = ViewGroup.LayoutParams.WRAP_CONTENT
                gravity = Gravity.BOTTOM
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
