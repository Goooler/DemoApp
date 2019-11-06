package io.goooler.demoapp.widget.vm

import android.app.Application
import androidx.databinding.ObservableField
import io.goooler.demoapp.R
import io.goooler.demoapp.base.BaseViewModel

class BottomTipDialogViewModel(application: Application) : BaseViewModel(application) {
    val title = ObservableField<String>()
    val content = ObservableField<String>()

    init {
        title.set(getString(R.string.tip))
    }
}