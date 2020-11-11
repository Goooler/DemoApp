package io.goooler.demoapp.main.model

import io.goooler.demoapp.adapter.rv.core.ISpanSize
import io.goooler.demoapp.adapter.rv.diff.IDiffVhModelType
import io.goooler.demoapp.main.R

class MainListItemModel(val logoUrl: String?, val content: String?) :
    IDiffVhModelType, ISpanSize {

    override val viewType: Int = R.layout.main_fragment_list_item

    override fun isItemTheSame(that: IDiffVhModelType): Boolean {
        return if (that is MainListItemModel) {
            this.content == that.content
        } else false
    }

    override fun isContentTheSame(that: IDiffVhModelType): Boolean = true
}