package io.goooler.demoapp.main.ui

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import io.goooler.demoapp.R
import io.goooler.demoapp.databinding.FragmentInviterBinding
import io.goooler.demoapp.util.LogUtil
import io.goooler.demoapp.util.SoftInputUtil

class InviterFragment : Fragment() {
    private val binding by lazy {
        DataBindingUtil.inflate<FragmentInviterBinding>(this.layoutInflater, R.layout.fragment_inviter, null, false)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        binding.nextStep.run {
            isEnabled = false
            setOnClickListener {
                binding.etInviteCode.run {
                    clearFocus()
                    SoftInputUtil.hideSoftInput(this)
                    if (text.toString().trim() == "666") {
                        (requireActivity() as? MainActivity)?.gotoLogin()
                    }
                }
            }
        }
        binding.etInviteCode.run {
            addTextChangedListener(object : TextWatcher {
                override fun afterTextChanged(s: Editable?) {
                    binding.nextStep.isEnabled = text.toString().trim().isNotEmpty()
                }

                override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
                }

                override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                }

            })
            onFocusChangeListener = View.OnFocusChangeListener { _, hasFocus ->
                if (hasFocus) {
                    LogUtil.d("focused")
                }
            }
            requestFocus()
            SoftInputUtil.showSoftInput(this)
        }
        return binding.root
    }

    companion object {
        fun newInstance() = InviterFragment()
    }
}
