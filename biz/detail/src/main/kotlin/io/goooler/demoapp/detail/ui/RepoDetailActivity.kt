package io.goooler.demoapp.detail.ui

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.padding
import androidx.compose.ui.Modifier
import io.goooler.demoapp.base.core.BaseActivity
import io.goooler.demoapp.detail.vm.DetailViewModel

class RepoDetailActivity : BaseActivity() {

  private val vm: DetailViewModel by viewModels()

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)

    vm.fullName = intent.getStringExtra(FULL_NAME) ?: return
    vm.refresh()

    setContent {
      DemoScaffold { innerPadding ->
        DetailPageWithSwipeRefresh(
          modifier = Modifier.padding(innerPadding),
        )
      }
    }
  }

  companion object {
    const val FULL_NAME = "fullName"
  }
}
