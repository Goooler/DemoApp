package io.goooler.demoapp.main.ui

import android.os.Bundle
import androidx.databinding.DataBindingUtil
import io.goooler.demoapp.R
import io.goooler.demoapp.base.BaseActivity
import io.goooler.demoapp.base.BaseApplication
import io.goooler.demoapp.databinding.ActivityMainBinding
import io.goooler.demoapp.util.SoftInputUtil

class MainActivity : BaseActivity() {

    private val binding by lazy {
        DataBindingUtil.setContentView<ActivityMainBinding>(this, R.layout.activity_main)
    }

    private val inviterFragment by lazy {
        InviterFragment.newInstance()
    }

    private val loginFragment by lazy {
        MainFragment.newInstance()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding
        addFragment(R.id.fragment_root, inviterFragment)
    }

    override fun onPause() {
        super.onPause()
        SoftInputUtil.hideSoftInput(this)
    }

    override fun onDestroy() {
        BaseApplication.destroyGlobalObject()
        super.onDestroy()
    }

    fun gotoLogin() {
        replaceFragment(R.id.fragment_root, loginFragment, true)
    }
}
