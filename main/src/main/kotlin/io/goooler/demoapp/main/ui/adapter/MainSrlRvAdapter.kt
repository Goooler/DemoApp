package io.goooler.demoapp.main.ui.adapter

import androidx.databinding.ViewDataBinding
import io.goooler.demoapp.adapter.rv.diff.BaseRvDiffAdapter
import io.goooler.demoapp.main.BR
import io.goooler.demoapp.main.model.MainListItemModel

class MainSrlRvAdapter(private val listener: OnEventListener) :
    BaseRvDiffAdapter<MainListItemModel>() {

    override fun onCreateVHForAll(binding: ViewDataBinding) {
        binding.setVariable(BR.listener, listener)
    }

    override fun onBindVHForAll(binding: ViewDataBinding, model: MainListItemModel) {
        binding.setVariable(BR.model, model)
    }

    fun interface OnEventListener {
        fun onContentClick(content: String)
    }
}