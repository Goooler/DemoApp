package io.goooler.demoapp.base.util

import androidx.activity.ComponentActivity
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.Fragment

class PermissionHelper private constructor(
  private val activity: ComponentActivity?,
  private val fragment: Fragment?
) {
  private val permissions = mutableListOf<String>()

  private var onRawResultsCallback: ((Map<String, Boolean>) -> Unit)? = null
  private var onAllGrantedCallback: (() -> Unit)? = null
  private var onGrantedCallback: ((List<String>) -> Unit)? = null
  private var onDeniedCallback: ((List<String>) -> Unit)? = null

  fun permissions(vararg permissions: String) = apply {
    this.permissions += permissions
  }

  fun permissions(permissions: List<String>) = apply {
    this.permissions += permissions
  }

  fun onRawResults(callback: ((Map<String, Boolean>) -> Unit)? = null) = apply {
    onRawResultsCallback = callback
  }

  fun onAllGranted(callback: (() -> Unit)? = null) = apply {
    onAllGrantedCallback = callback
  }

  fun onGranted(callback: ((List<String>) -> Unit)? = null) = apply {
    onGrantedCallback = callback
  }

  fun onDenied(callback: ((List<String>) -> Unit)? = null) = apply {
    onDeniedCallback = callback
  }

  fun request() {
    val grantedPermissions = mutableListOf<String>()
    val deniedPermissions = mutableListOf<String>()

    val launcher = activity ?: fragment
      ?: throw IllegalArgumentException("activity or fragment must not be null")
    launcher.registerForActivityResult(
      ActivityResultContracts.RequestMultiplePermissions()
    ) {
      onRawResultsCallback?.invoke(it) ?: run {
        for (entry in it.entries) {
          val list = if (entry.value) grantedPermissions else deniedPermissions
          list += entry.key
        }
        onGrantedCallback?.invoke(grantedPermissions)
        onDeniedCallback?.invoke(deniedPermissions)
        if (grantedPermissions.isNotEmpty() && grantedPermissions.size == it.size) {
          onAllGrantedCallback?.invoke()
        }
      }
    }.launch(permissions.toTypedArray())
  }

  companion object {
    fun with(activity: ComponentActivity): PermissionHelper =
      PermissionHelper(activity, null)

    fun with(fragment: Fragment): PermissionHelper =
      PermissionHelper(null, fragment)
  }
}
