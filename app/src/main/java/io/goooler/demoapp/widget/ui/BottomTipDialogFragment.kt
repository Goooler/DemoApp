package io.goooler.demoapp.widget.ui

import android.os.Bundle
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.FragmentManager
import io.goooler.demoapp.R
import io.goooler.demoapp.base.BaseDialogFragment
import io.goooler.demoapp.databinding.BottomTipDialogFragmentBinding
import io.goooler.demoapp.util.unsafeLazy
import io.goooler.demoapp.widget.vm.BottomTipDialogViewModel

class BottomTipDialogFragment : BaseDialogFragment() {

    private val vm by unsafeLazy { getViewModel(BottomTipDialogViewModel::class.java) }

    private val binding by unsafeLazy { BottomTipDialogFragmentBinding.inflate(layoutInflater) }

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
        binding.vm = vm
        binding.listener = eventListener
        arguments?.let {
            vm.title.set(it.getString(TITLE))
            vm.content.set(it.getString(CONTENT))
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

    private val eventListener = object : EventListener {
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

        fun newInstance(title: String, content: String) = BottomTipDialogFragment().apply {
            arguments = Bundle().apply {
                putString(TITLE, title)
                putString(CONTENT, content)
            }
        }

        fun show(manager: FragmentManager, title: String, content: String, tag: String? = null) {
            newInstance(title, content).show(manager, tag)
        }
    }
}