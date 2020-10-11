package io.goooler.demoapp.main.model

import io.goooler.demoapp.adapter.rv.diff.IModelDiff
import io.goooler.demoapp.main.R

class MainListItemModel(val logoUrl: String?, val content: String?) :
    IModelDiff<MainListItemModel> {

    override val viewType: Int = R.layout.main_fragment_list_item

    override fun isItemTheSame(that: MainListItemModel): Boolean = this.content == that.content

    override fun isContentTheSame(that: MainListItemModel): Boolean = true
}