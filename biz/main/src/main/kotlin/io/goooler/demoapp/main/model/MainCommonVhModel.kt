package io.goooler.demoapp.main.model

import android.os.Parcelable
import io.goooler.demoapp.adapter.rv.core.ISpanSize
import io.goooler.demoapp.adapter.rv.diff.IDiffVhModelType
import io.goooler.demoapp.main.R
import kotlinx.parcelize.IgnoredOnParcel
import kotlinx.parcelize.Parcelize

sealed class MainCommonVhModel : IDiffVhModelType, ISpanSize {

  @Parcelize
  class Repo(
    val logoUrl: String?,
    val content: String?,
    val fullName: String = "",
    var likeCount: Int = 0
  ) :
    MainCommonVhModel(), Parcelable {

    val likeCountStr: String get() = likeCount.toString()

    @IgnoredOnParcel
    override val viewType: Int = R.layout.main_common_rv_item

    @IgnoredOnParcel
    override val spanSize: Int = ISpanSize.SPAN_SIZE_SINGLE

    override fun isItemTheSame(that: IDiffVhModelType): Boolean {
      return if (that is Repo) {
        that.fullName == this.fullName && that.likeCount == this.likeCount
      } else
        false
    }

    override fun isContentTheSame(that: IDiffVhModelType): Boolean = true

    interface OnEventListener {
      fun onContentClick(fullName: String) {}
      fun onItemClick(item: MainCommonVhModel) {}
    }
  }

  class Empty : MainCommonVhModel() {
    override val viewType: Int = io.goooler.demoapp.common.R.layout.common_empty_layout
  }

  class Error : MainCommonVhModel() {
    override val viewType: Int = io.goooler.demoapp.common.R.layout.common_error_layout
  }
}
