package io.goooler.demoapp.main.model

import io.goooler.demoapp.adapter.rv.diff.IModelDiff
import io.goooler.demoapp.main.R

class MainListItemModel(val logoUrl: String?, val content: String?) :
    IModelDiff<MainListItemModel> {

    override val viewType: Int = R.layout.main_fragment_list_item

    override fun isContentTheSame(other: MainListItemModel): Boolean =
        this.content == other.content

    override fun isItemTheSame(other: MainListItemModel): Boolean = this === other
}