package io.goooler.demoapp.widget.ui

import android.content.DialogInterface
import android.os.Bundle
import android.view.KeyEvent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.FragmentManager
import io.goooler.demoapp.R
import io.goooler.demoapp.base.BaseDialogFragment
import io.goooler.demoapp.databinding.FullScreenDialogFragmentBinding
import io.goooler.demoapp.util.unsafeLazy

class FullScreenDialogFragment : BaseDialogFragment() {

    private val binding by unsafeLazy { FullScreenDialogFragmentBinding.inflate(layoutInflater) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setStyle(STYLE_NORMAL, R.style.DialogTransparentFullWindowTheme)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding.lifecycleOwner = this
        binding.listener = eventListener
        dialog?.setOnKeyListener(eventListener)
        return binding.root
    }

    private val eventListener = object : View.OnClickListener, DialogInterface.OnKeyListener {
        override fun onClick(v: View?) {
            when (v) {
                binding.ivCenter -> {
                    dismiss()
                }
            }
        }

        override fun onKey(dialog: DialogInterface?, keyCode: Int, event: KeyEvent?): Boolean {
            return when (keyCode) {
                KeyEvent.KEYCODE_BACK -> true
                else -> false
            }
        }
    }

    companion object {
        fun show(manager: FragmentManager) {
            FullScreenDialogFragment().show(manager, null)
        }
    }
}