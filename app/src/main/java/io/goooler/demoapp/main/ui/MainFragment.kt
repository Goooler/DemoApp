package io.goooler.demoapp.main.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import io.goooler.demoapp.R
import io.goooler.demoapp.databinding.FragmentMainBinding

class MainFragment : Fragment() {
    private val binding by lazy {
        DataBindingUtil.inflate<FragmentMainBinding>(this.layoutInflater, R.layout.fragment_main, null, false)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        return binding.root
    }

    companion object {
        fun newInstance() = MainFragment()
    }
}
