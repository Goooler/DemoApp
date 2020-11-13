package io.goooler.demoapp.main.ui.adapter

import androidx.databinding.ViewDataBinding
import io.goooler.demoapp.adapter.rv.paging.BaseRvPagingAdapter
import io.goooler.demoapp.main.BR
import io.goooler.demoapp.main.model.MainCommonRepoVhModel
import io.goooler.demoapp.main.model.MainCommonVhModel

class MainPagingRvAdapter(private val listener: OnEventListener) :
    BaseRvPagingAdapter<MainCommonVhModel>() {

    override fun onCreateVHForAll(binding: ViewDataBinding) {
        binding.setVariable(BR.listener, listener)
    }

    override fun onBindVHForAll(binding: ViewDataBinding, model: MainCommonVhModel) {
        binding.setVariable(BR.model, model)
    }

    fun interface OnEventListener : MainCommonRepoVhModel.OnEventListener
}