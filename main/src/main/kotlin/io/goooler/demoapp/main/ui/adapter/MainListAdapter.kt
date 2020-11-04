package io.goooler.demoapp.main.ui.adapter

import androidx.databinding.ViewDataBinding
import io.goooler.demoapp.adapter.rv.base.IModelType
import io.goooler.demoapp.adapter.rv.list.BaseListAdapter
import io.goooler.demoapp.main.BR

class MainListAdapter(private val listener: OnEventListener) :
    BaseListAdapter<IModelType>() {

    override fun onCreateVHForAll(binding: ViewDataBinding) {
        binding.setVariable(BR.listener, listener)
    }

    override fun onBindVHForAll(binding: ViewDataBinding, model: IModelType) {
        binding.setVariable(BR.model, model)
    }

    fun interface OnEventListener {
        fun onContentClick(content: String)
    }
}