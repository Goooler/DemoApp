package io.goooler.demoapp.detail.ui

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Share
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.pullrefresh.PullRefreshIndicator
import androidx.compose.material.pullrefresh.pullRefresh
import androidx.compose.material.pullrefresh.rememberPullRefreshState
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import io.goooler.demoapp.common.util.getQuantityString
import io.goooler.demoapp.common.util.showToast
import io.goooler.demoapp.detail.R
import io.goooler.demoapp.detail.model.RepoDetailModel

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun DetailPageWithSwipeRefresh(
  isRefreshing: Boolean,
  onRefresh: () -> Unit,
  model: RepoDetailModel,
  modifier: Modifier = Modifier,
  onForkClick: () -> Unit,
) {
  val refreshState = rememberPullRefreshState(refreshing = isRefreshing, onRefresh = onRefresh)

  Box(modifier = Modifier.pullRefresh(state = refreshState)) {
    DetailPage(model = model, onForkClick = onForkClick)
    PullRefreshIndicator(isRefreshing, refreshState, Modifier.align(Alignment.TopCenter))
  }
}

@Composable
fun DetailPage(
  model: RepoDetailModel,
  modifier: Modifier = Modifier,
  onForkClick: () -> Unit,
) {
  var isDescExpanded by remember { mutableStateOf(false) }

  Column(
    modifier = Modifier
      .padding(8.dp)
      .fillMaxSize()
      .verticalScroll(rememberScrollState()),
  ) {
    Text(
      text = model.fullName,
      style = MaterialTheme.typography.titleLarge,
      maxLines = 1,
    )
    Spacer(modifier = Modifier.height(5.dp))
    Text(
      text = model.description,
      style = MaterialTheme.typography.bodyMedium,
      maxLines = if (isDescExpanded) Int.MAX_VALUE else 2,
      modifier = Modifier.clickable {
        isDescExpanded = !isDescExpanded
      },
    )
    Spacer(modifier = Modifier.height(5.dp))
    Row {
      Button(onClick = {
        R.plurals.detail_star_count_tip.getQuantityString(model.starsCount)?.showToast()
      }) {
        Icon(
          Icons.Filled.Star,
          contentDescription = "Star",
          modifier = Modifier.size(ButtonDefaults.IconSize),
        )
        Spacer(Modifier.size(ButtonDefaults.IconSpacing))
        Text(model.starsCount.toString())
      }
      Spacer(modifier = Modifier.width(20.dp))
      Button(onClick = onForkClick) {
        Icon(
          Icons.Filled.Share,
          contentDescription = "Fork",
          modifier = Modifier.size(ButtonDefaults.IconSize),
        )
        Spacer(Modifier.size(ButtonDefaults.IconSpacing))
        Text(model.forksCount.toString())
      }
    }
    Spacer(modifier = Modifier.height(5.dp))
  }
}

@Preview
@Composable
private fun DetailPagePreview() {
  @Suppress("MagicNumber")
  val model = RepoDetailModel(
    "Compose/Demo",
    "Jetpack Compose is Android’s modern toolkit for building native UI. " +
      "It simplifies and accelerates UI development on Android. " +
      "Quickly bring your app to life with less code, powerful tools, and intuitive Kotlin APIs.",
    "Apache",
    99,
    1,
    2,
  )
  DetailPage(model) {}
}
