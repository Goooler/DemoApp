package io.goooler.demoapp.main.model

import io.github.goooler.adapter.rv.core.ISpanSize
import io.github.goooler.adapter.rv.diff.IDiffVhModelType
import io.goooler.demoapp.main.R

sealed interface MainCommonVhModel :
  IDiffVhModelType,
  ISpanSize {

  data class Repo(
    val logoUrl: String?,
    val content: String?,
    val fullName: String,
    val shareCount: Int = 0,
  ) : MainCommonVhModel {

    override val viewType: Int = R.layout.main_common_rv_item

    override val spanSize: Int = ISpanSize.SPAN_SIZE_SINGLE

    override fun isItemTheSame(that: IDiffVhModelType): Boolean =
      (that as? Repo)?.fullName == this.fullName

    override fun getPayloads(that: IDiffVhModelType): String? = when {
      that !is Repo -> null
      that.logoUrl != this@Repo.logoUrl -> KEY_LOGO_URL
      that.content != this@Repo.content -> KEY_CONTENT
      that.shareCount != this@Repo.shareCount -> KEY_SHARE_COUNT
      else -> null
    }

    val shareCountStr: String get() = shareCount.toString()

    interface OnEventListener {
      fun onContentClick(fullName: String) {}
      fun onShareClick(fullName: String) {}
      fun onItemClick(item: MainCommonVhModel) {}
    }

    companion object {
      internal const val KEY_LOGO_URL = "logoUrl"
      internal const val KEY_CONTENT = "content"
      internal const val KEY_SHARE_COUNT = "shareCount"
    }
  }

  data object Empty : MainCommonVhModel {
    override val viewType: Int = io.goooler.demoapp.common.R.layout.common_empty_layout
  }

  data object Error : MainCommonVhModel {
    override val viewType: Int = io.goooler.demoapp.common.R.layout.common_error_layout
  }
}
