package io.goooler.demoapp.detail.ui

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import io.goooler.demoapp.base.core.BaseActivity
import io.goooler.demoapp.detail.vm.DetailViewModel

class RepoDetailActivity : BaseActivity() {

  private val vm: DetailViewModel by viewModels()

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)

    intent.getStringExtra(FULL_NAME)?.let {
      vm.fullName = it
    }

    setContent {
      val model = vm.repoDetailModel.observeAsState().value
        ?: throw IllegalArgumentException("RepoDetailModel has not been initialized")
      val isRefreshing by vm.isRefreshing.observeAsState(true)
      DetailPageWithSwipeRefresh(isRefreshing, vm::refresh, model, vm::fork)
    }
  }

  companion object {
    const val FULL_NAME = "fullName"
  }
}
