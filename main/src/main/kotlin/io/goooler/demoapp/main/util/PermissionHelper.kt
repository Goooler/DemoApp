package io.goooler.demoapp.main.util

import androidx.activity.ComponentActivity
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts

class PermissionHelper(private val activity: ComponentActivity) {
  private val permissions = mutableListOf<String>()
  private var grantedPermissionCount = 0
  private var onAllPermissionsGrantedCallback: (() -> Unit)? = null

  private lateinit var requestPermissionsLauncher: ActivityResultLauncher<Array<String>>

  fun permissions(vararg permissions: String) = apply {
    this.permissions.addAll(permissions)
  }

  fun permissions(permissions: List<String>) = apply {
    this.permissions.addAll(permissions)
  }

  fun onAllPermissionsGranted(callback: (() -> Unit)? = null) = apply {
    onAllPermissionsGrantedCallback = callback
  }

  fun request() {
    requestPermissionsLauncher = activity.registerForActivityResult(
      ActivityResultContracts.RequestMultiplePermissions()
    ) {
      it.entries.forEach { entry ->
        if (entry.value) grantedPermissionCount++
      }
      if (grantedPermissionCount != 0 && grantedPermissionCount == it.size) {
        onAllPermissionsGrantedCallback?.invoke()
      }
    }
    requestPermissionsLauncher.launch(permissions.toTypedArray())
  }

  companion object {
    fun with(activity: ComponentActivity): PermissionHelper =
      PermissionHelper(activity)
  }
}
