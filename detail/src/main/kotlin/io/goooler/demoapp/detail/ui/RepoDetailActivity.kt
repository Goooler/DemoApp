package io.goooler.demoapp.detail.ui

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.lifecycleScope
import io.goooler.demoapp.base.core.BaseActivity
import io.goooler.demoapp.detail.vm.DetailViewModel
import kotlinx.coroutines.launch

class RepoDetailActivity : BaseActivity() {

  private val vm: DetailViewModel by viewModels()

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)

    lifecycleScope.launch {
      vm.getRepoDetail()
    }

    setContent {
      HelloScreen()
    }
  }
}

@Preview
@Composable
fun HelloScreen() {
  Text(text = "Hello Compose")
}
