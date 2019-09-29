package io.goooler.demoapp.activity

import android.net.Uri
import android.os.Bundle
import android.view.View

import androidx.databinding.DataBindingUtil

import io.goooler.demoapp.R
import io.goooler.demoapp.async.ClickHandler
import io.goooler.demoapp.base.BaseActivity
import io.goooler.demoapp.base.BaseApplication
import io.goooler.demoapp.databinding.ActivityMainBinding
import io.goooler.demoapp.util.ToastUtil

class MainActivity : BaseActivity(), BlankFragment.OnFragmentInteractionListener {

    private val binding by lazy {
        DataBindingUtil.setContentView<ActivityMainBinding>(this, R.layout.activity_main)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding
        addFragment(R.id.fragment_root, BlankFragment.newInstance())
    }

    override fun onFragmentInteraction(uri: Uri) {
        //do nothing
    }

    override fun onDestroy() {
        BaseApplication.destroyGlobalObject()
        super.onDestroy()
    }
}
