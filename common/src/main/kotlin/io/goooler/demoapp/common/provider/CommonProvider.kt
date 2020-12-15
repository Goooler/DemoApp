package io.goooler.demoapp.common.provider

import android.annotation.TargetApi
import android.os.Build
import androidx.core.content.FileProvider

/**
 * 7.0 以上行为变更，不可直接 Uri.fromFile()，使用 FileProvider 引用文件
 */
@TargetApi(Build.VERSION_CODES.N)
class CommonProvider : FileProvider()
