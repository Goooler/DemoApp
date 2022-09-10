package io.goooler.demoapp.detail.ui

import androidx.compose.foundation.clickable
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
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Share
import androidx.compose.material.icons.filled.Star
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.google.accompanist.swiperefresh.SwipeRefresh
import com.google.accompanist.swiperefresh.rememberSwipeRefreshState
import io.goooler.demoapp.common.util.getQuantityString
import io.goooler.demoapp.common.util.showToast
import io.goooler.demoapp.detail.R
import io.goooler.demoapp.detail.model.RepoDetailModel

@Composable
fun DetailPageWithSwipeRefresh(
  isRefreshing: Boolean,
  onRefresh: () -> Unit,
  model: RepoDetailModel,
  onForkClick: () -> Unit,
) {
  MaterialTheme {
    SwipeRefresh(state = rememberSwipeRefreshState(isRefreshing), onRefresh = onRefresh) {
      DetailPage(model, onForkClick)
    }
  }
}

@Composable
fun DetailPage(model: RepoDetailModel, onForkClick: () -> Unit) {
  var isDescExpanded by remember { mutableStateOf(false) }

  Column(
    modifier = Modifier
      .padding(8.dp)
      .fillMaxSize()
      .verticalScroll(rememberScrollState()),
  ) {
    Text(
      text = model.fullName,
      style = MaterialTheme.typography.h5,
      maxLines = 1,
    )
    Spacer(modifier = Modifier.height(5.dp))
    Text(
      text = model.description,
      style = MaterialTheme.typography.body1,
      maxLines = if (isDescExpanded) Int.MAX_VALUE else 2,
      modifier = Modifier.clickable {
        isDescExpanded = !isDescExpanded
      },
    )
    Spacer(modifier = Modifier.height(5.dp))
    Row {
      Button(onClick = {
        R.plurals.detail_star_count_tip.getQuantityString(model.starsCount)?.showToast()
      },) {
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
fun DetailPagePreview() {
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
