package io.goooler.demoapp.common.ui

import android.os.Bundle
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.FragmentManager
import io.goooler.demoapp.base.R
import io.goooler.demoapp.base.core.BaseDialogFragment
import io.goooler.demoapp.base.util.unsafeLazy
import io.goooler.demoapp.common.databinding.CommonBottomTipDialogFragmentBinding

class BottomTipDialogFragment : BaseDialogFragment() {

    private val binding by unsafeLazy { CommonBottomTipDialogFragmentBinding.inflate(layoutInflater) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setStyle(STYLE_NORMAL, R.style.DialogBottomAnim)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding.lifecycleOwner = this
        binding.listener = eventListener
        arguments?.let {
            binding.tvTitle.text = it.getString(TITLE)
            binding.tvContent.text = it.getString(CONTENT)
        }
        return binding.root
    }

    override fun onResume() {
        super.onResume()
        setStyle()
    }

    private fun setStyle() {
        setStyle(STYLE_NORMAL, R.style.DialogTransparentTheme)
        val param = dialog?.window?.attributes?.apply {
            width = ViewGroup.LayoutParams.MATCH_PARENT
            height = ViewGroup.LayoutParams.WRAP_CONTENT
            gravity = Gravity.BOTTOM
        }
        dialog?.window?.run {
            setWindowAnimations(R.style.DialogBottomAnim)
            attributes = param
        }
    }

    private val eventListener = object :
        EventListener {
        override fun onCloseClick() {
            dismiss()
        }
    }

    interface EventListener {
        fun onCloseClick()
    }

    companion object {
        private const val TITLE = "title"
        private const val CONTENT = "content"

        fun newInstance(title: String, content: String) = BottomTipDialogFragment()
            .apply {
                arguments = Bundle().apply {
                    putString(TITLE, title)
                    putString(CONTENT, content)
                }
            }

        fun show(manager: FragmentManager, title: String, content: String, tag: String? = null) {
            newInstance(
                title,
                content
            ).show(manager, tag)
        }
    }
}