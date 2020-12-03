package io.goooler.demoapp.main.ui.adapter

import androidx.databinding.ViewDataBinding
import io.goooler.demoapp.adapter.rv.diff.BaseRvDiffAdapter
import io.goooler.demoapp.main.model.MainCommonVhModel
import io.goooler.demoapp.main.util.bindListener
import io.goooler.demoapp.main.util.bindModel

class MainSrlRvAdapter(private val listener: OnEventListener) :
    BaseRvDiffAdapter<MainCommonVhModel>() {

    override fun onCreateVHForAll(binding: ViewDataBinding) {
        binding.bindListener(listener)
    }

    override fun onBindVHForAll(binding: ViewDataBinding, model: MainCommonVhModel) {
        binding.bindModel(model)
    }

    interface OnEventListener : MainCommonVhModel.Repo.OnEventListener
}