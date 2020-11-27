package io.goooler.demoapp.base.core

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.DefaultLifecycleObserver

abstract class BaseViewModel(application: Application) : AndroidViewModel(application),
    DefaultLifecycleObserver