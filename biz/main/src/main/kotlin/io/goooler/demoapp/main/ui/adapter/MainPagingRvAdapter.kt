package io.goooler.demoapp.main.ui.adapter

import androidx.databinding.ViewDataBinding
import io.goooler.demoapp.adapter.rv.paging.BaseRvPagingAdapter
import io.goooler.demoapp.main.model.MainCommonVhModel
import io.goooler.demoapp.main.util.bindListener
import io.goooler.demoapp.main.util.bindModel

class MainPagingRvAdapter(private val listener: OnEventListener) :
  BaseRvPagingAdapter<MainCommonVhModel>() {

  override fun onCreateVHForAll(binding: ViewDataBinding) {
    binding.bindListener(listener)
  }

  override fun onBindVHForAll(binding: ViewDataBinding, model: MainCommonVhModel) {
    binding.bindModel(model)
  }

  interface OnEventListener : MainCommonVhModel.Repo.OnEventListener
}
