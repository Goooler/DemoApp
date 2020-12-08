package io.goooler.demoapp.main.model

import io.goooler.demoapp.adapter.rv.core.ISpanSize
import io.goooler.demoapp.adapter.rv.diff.IDiffVhModelType
import io.goooler.demoapp.main.R

sealed class MainCommonVhModel : IDiffVhModelType, ISpanSize {

    class Repo(val logoUrl: String?, val content: String?) : MainCommonVhModel() {

        override val viewType: Int = R.layout.main_common_rv_item

        override val spanSize: Int = ISpanSize.SPAN_SIZE_SINGLE

        override fun isItemTheSame(that: IDiffVhModelType): Boolean {
            return if (that is Repo) {
                this.content == that.content
            } else false
        }

        override fun isContentTheSame(that: IDiffVhModelType): Boolean = true

        interface OnEventListener {
            fun onContentClick(content: String)
        }
    }
}
