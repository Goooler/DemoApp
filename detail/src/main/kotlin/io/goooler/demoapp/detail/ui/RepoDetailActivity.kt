package io.goooler.demoapp.detail.ui

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import io.goooler.demoapp.base.core.BaseActivity

class RepoDetailActivity : BaseActivity() {

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
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
