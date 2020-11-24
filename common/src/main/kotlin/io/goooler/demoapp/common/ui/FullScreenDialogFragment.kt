package io.goooler.demoapp.common.ui

import android.os.Bundle
import android.view.KeyEvent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.FragmentManager
import io.goooler.demoapp.base.R
import io.goooler.demoapp.base.util.unsafeLazy
import io.goooler.demoapp.common.base.BaseThemeDialogFragment
import io.goooler.demoapp.common.databinding.FullScreenDialogFragmentBinding

class FullScreenDialogFragment private constructor() : BaseThemeDialogFragment() {

    private val binding by unsafeLazy {
        FullScreenDialogFragmentBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setStyle(STYLE_NO_TITLE, R.style.DialogFullScreenTheme)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding.lifecycleOwner = this
        binding.ivCenter.setOnClickListener {
            dismiss()
        }
        dialog?.run {
            setCanceledOnTouchOutside(isCancelable)
            setCancelable(isCancelable)
            setOnKeyListener { _, keyCode, _ ->
                when (keyCode) {
                    KeyEvent.KEYCODE_BACK -> true
                    else -> false
                }
            }
        }
        return binding.root
    }

    override fun isCancelable(): Boolean = false

    companion object {
        fun show(manager: FragmentManager) = FullScreenDialogFragment().show(manager, null)
    }
}