package io.goooler.demoapp.main.util

import androidx.activity.ComponentActivity
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.Fragment

class PermissionHelper private constructor(
  private val activity: ComponentActivity?,
  private val fragment: Fragment?
) {
  private val permissions = mutableListOf<String>()
  private var grantedPermissionCount = 0
  private var onRawResultsCallback: ((Map<String, Boolean>) -> Unit)? = null
  private var onAllPermissionsGrantedCallback: (() -> Unit)? = null

  private lateinit var requestPermissionsLauncher: ActivityResultLauncher<Array<String>>

  fun permissions(vararg permissions: String) = apply {
    this.permissions.addAll(permissions)
  }

  fun permissions(permissions: List<String>) = apply {
    this.permissions.addAll(permissions)
  }

  fun onRawResults(callback: ((Map<String, Boolean>) -> Unit)? = null) {
    onRawResultsCallback = callback
  }

  fun onAllPermissionsGranted(callback: (() -> Unit)? = null) = apply {
    onAllPermissionsGrantedCallback = callback
  }

  fun request() {
    val launcher = activity ?: fragment
      ?: throw IllegalArgumentException("activity or fragment must not be null")
    requestPermissionsLauncher = launcher.registerForActivityResult(
      ActivityResultContracts.RequestMultiplePermissions()
    ) {
      onRawResultsCallback?.invoke(it) ?: run {
        for (entry in it.entries) {
          if (entry.value) grantedPermissionCount++
        }
        if (grantedPermissionCount != 0 && grantedPermissionCount == it.size) {
          onAllPermissionsGrantedCallback?.invoke()
        }
      }
    }
    requestPermissionsLauncher.launch(permissions.toTypedArray())
  }

  companion object {
    fun with(activity: ComponentActivity): PermissionHelper =
      PermissionHelper(activity, null)

    fun with(fragment: Fragment): PermissionHelper =
      PermissionHelper(null, fragment)
  }
}
