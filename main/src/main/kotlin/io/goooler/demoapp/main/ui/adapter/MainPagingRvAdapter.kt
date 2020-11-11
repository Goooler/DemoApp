package io.goooler.demoapp.main.ui.adapter

import androidx.databinding.ViewDataBinding
import io.goooler.demoapp.adapter.rv.paging.BasePagingRvAdapter
import io.goooler.demoapp.main.BR
import io.goooler.demoapp.main.model.MainListItemModel

class MainPagingRvAdapter(private val listener: MainSrlRvAdapter.OnEventListener) :
    BasePagingRvAdapter<MainListItemModel>() {

    override fun onCreateVHForAll(binding: ViewDataBinding) {
        binding.setVariable(BR.listener, listener)
    }

    override fun onBindVHForAll(binding: ViewDataBinding, model: MainListItemModel) {
        binding.setVariable(BR.model, model)
    }
}