package io.goooler.demoapp.detail.ui

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
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
      DemoTheme {
        Scaffold(
          modifier = Modifier.fillMaxSize(),
        ) { innerPadding ->
          DetailPageWithSwipeRefresh(
            modifier = Modifier.padding(innerPadding),
            isRefreshing = isRefreshing,
            onRefresh = vm::refresh,
            model = model,
            onForkClick = vm::fork,
          )
        }
      }
    }
  }

  companion object {
    const val FULL_NAME = "fullName"
  }
}
