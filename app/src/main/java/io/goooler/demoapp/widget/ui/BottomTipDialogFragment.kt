package io.goooler.demoapp.widget.ui

import android.os.Bundle
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.FragmentManager
import io.goooler.demoapp.R
import io.goooler.demoapp.base.BaseDialogFragment
import io.goooler.demoapp.databinding.FragmentBottomTipDialogBinding
import io.goooler.demoapp.widget.vm.BottomTipDialogViewModel

class BottomTipDialogFragment : BaseDialogFragment() {

    private val vm by lazy { getViewModel(BottomTipDialogViewModel::class.java) }

    private val binding by lazy {
        DataBindingUtil.inflate<FragmentBottomTipDialogBinding>(layoutInflater, R.layout.fragment_bottom_tip_dialog, null, false)
    }

    private val clickListener = View.OnClickListener {
        when (it.id) {
            R.id.iv_close -> dismiss()
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setStyle(STYLE_NORMAL, R.style.DialogFullScreen)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding.vm = vm
        binding.clickListener = clickListener
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

    companion object {
        private const val TITLE = "title"
        private const val CONTENT = "content"

        fun show(manager: FragmentManager, title: String, content: String) = BottomTipDialogFragment().apply {
            arguments = Bundle().apply {
                putString(TITLE, title)
                putString(CONTENT, content)
            }
        }.show(manager, null)
    }
}