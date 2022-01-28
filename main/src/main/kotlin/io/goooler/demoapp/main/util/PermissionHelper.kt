package io.goooler.demoapp.main.util

import androidx.activity.result.ActivityResultCaller
import androidx.lifecycle.DefaultLifecycleObserver
import androidx.lifecycle.LifecycleOwner

class PermissionHelper : DefaultLifecycleObserver {

  companion object {
    fun <T> with(owner: T): PermissionHelper
      where T : ActivityResultCaller, T : LifecycleOwner =
      PermissionHelper().apply(owner.lifecycle::addObserver)
  }
}
