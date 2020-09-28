package io.goooler.demoapp.main.model

import io.goooler.demoapp.adapter.rv.base.IModelType
import io.goooler.demoapp.main.R

class MainListItemModel(val logoUrl: String?, val content: String?) : IModelType {

    override val viewType: Int = R.layout.main_fragment_list_item
}