package io.goooler.demoapp.main.vm

import android.app.Application
import androidx.databinding.ObservableField
import io.goooler.demoapp.R
import io.goooler.demoapp.base.BaseViewModel

class MainViewModel(application: Application) : BaseViewModel(application) {
    val title = ObservableField<String>()

    init {
        title.set(getString(R.string.main_fragment))
    }
}