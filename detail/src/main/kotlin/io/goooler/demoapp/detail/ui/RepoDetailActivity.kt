package io.goooler.demoapp.detail.ui

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import io.goooler.demoapp.base.core.BaseActivity
import io.goooler.demoapp.detail.vm.DetailViewModel

class RepoDetailActivity : BaseActivity() {

  private val vm: DetailViewModel by viewModels()

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)

    intent.getStringExtra(FULL_NAME)?.let {
      vm.fullName = it
      vm.refresh()
    }

    setContent {
      val model by vm.repoDetailModel.collectAsState()
      val isRefreshing by vm.isRefreshing.collectAsState()
      DetailPageWithSwipeRefresh(isRefreshing, vm::refresh, model, vm::fork)
    }
  }

  companion object {
    const val FULL_NAME = "fullName"
  }
}
