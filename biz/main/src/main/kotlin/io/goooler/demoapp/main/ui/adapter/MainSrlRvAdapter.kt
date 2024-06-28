package io.goooler.demoapp.main.ui.adapter

import androidx.databinding.ViewDataBinding
import io.github.goooler.adapter.rv.diff.BaseRvDiffAdapter
import io.github.goooler.adapter.rv.diff.DiffCallback
import io.goooler.demoapp.common.util.ImageLoader
import io.goooler.demoapp.common.util.asConfig
import io.goooler.demoapp.main.databinding.MainCommonRvItemBinding
import io.goooler.demoapp.main.model.MainCommonVhModel
import io.goooler.demoapp.main.util.bindListener
import io.goooler.demoapp.main.util.bindModel

class MainSrlRvAdapter(
  private val listener: OnEventListener,
) : BaseRvDiffAdapter<MainCommonVhModel>(DiffCallback<MainCommonVhModel>().asConfig()) {

  override fun onCreateVH(binding: ViewDataBinding) {
    binding.bindListener(listener)
  }

  override fun onBindVH(
    binding: ViewDataBinding,
    model: MainCommonVhModel,
    payloads: List<Any>,
  ) {
    if (payloads.isEmpty()) {
      binding.bindModel(model)
    } else {
      binding as MainCommonRvItemBinding
      model as MainCommonVhModel.Repo
      payloads.forEach {
        when (it) {
          MainCommonVhModel.Repo.KEY_LOGO_URL -> ImageLoader.load(binding.ivLogo, model.logoUrl)
          MainCommonVhModel.Repo.KEY_CONTENT -> binding.tvContent.text = model.content
          MainCommonVhModel.Repo.KEY_SHARE_COUNT -> binding.tvLikeCount.text = model.shareCountStr
        }
      }
    }
  }

  interface OnEventListener : MainCommonVhModel.Repo.OnEventListener
}
