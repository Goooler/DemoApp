package io.goooler.demoapp.detail.ui

import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.spring
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Share
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.pulltorefresh.PullToRefreshContainer
import androidx.compose.material3.pulltorefresh.rememberPullToRefreshState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import io.goooler.demoapp.common.util.getQuantityString
import io.goooler.demoapp.common.util.showToast
import io.goooler.demoapp.detail.R
import io.goooler.demoapp.detail.model.RepoDetailModel
import io.goooler.demoapp.detail.vm.DetailViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DetailScreenWithSwipeRefresh(
  modifier: Modifier = Modifier,
  vm: DetailViewModel = viewModel(),
) {
  val model by vm.repoDetailModel.collectAsState()
  val isRefreshing by vm.isRefreshing.collectAsState()
  val refreshState = rememberPullToRefreshState()

  if (refreshState.isRefreshing) {
    LaunchedEffect(true) {
      // fetch something
      vm.refresh()
      if (!isRefreshing) {
        refreshState.endRefresh()
      }
    }
  }

  Surface(
    color = MaterialTheme.colorScheme.surface,
    modifier = modifier.padding(horizontal = 10.dp),
  ) {
    Box(Modifier.nestedScroll(refreshState.nestedScrollConnection)) {
      DetailList(model, vm::fork)

      PullToRefreshContainer(
        modifier = Modifier.align(Alignment.TopCenter),
        state = refreshState,
      )
    }
  }
}

@Composable
private fun DetailList(
  model: RepoDetailModel,
  onForkClick: () -> Unit = {},
) {
  LazyColumn {
    @Suppress("MagicNumber")
    val models = List(10) { model }
    items(models) { model ->
      DetailCard(model = model, onForkClick = onForkClick)
    }
  }
}

@Composable
private fun DetailCard(
  model: RepoDetailModel,
  modifier: Modifier = Modifier,
  onForkClick: () -> Unit = {},
) {
  var isDescExpanded by rememberSaveable { mutableStateOf(false) }

  Card(
    modifier = modifier.padding(8.dp),
  ) {
    Column(
      modifier = Modifier
        .padding(12.dp)
        .animateContentSize(
          animationSpec = spring(
            dampingRatio = Spring.DampingRatioMediumBouncy,
            stiffness = Spring.StiffnessLow,
          ),
        ),
    ) {
      Text(
        text = model.fullName,
        style = MaterialTheme.typography.titleLarge.copy(
          fontWeight = FontWeight.SemiBold,
        ),
        maxLines = 1,
      )
      Spacer(modifier = Modifier.height(5.dp))
      Text(
        text = model.description,
        style = MaterialTheme.typography.bodyLarge,
        maxLines = if (isDescExpanded) Int.MAX_VALUE else 1,
        modifier = Modifier.clickable {
          isDescExpanded = !isDescExpanded
        },
      )
      Spacer(modifier = Modifier.height(5.dp))
      Row {
        Button(
          modifier = Modifier.weight(1f),
          onClick = {
            R.plurals.detail_star_count_tip.getQuantityString(model.starsCount)?.showToast()
          },
        ) {
          Icon(
            Icons.Filled.Star,
            contentDescription = "Star",
            modifier = Modifier.size(ButtonDefaults.IconSize),
          )
          Spacer(Modifier.size(ButtonDefaults.IconSpacing))
          Text(model.starsCount.toString())
        }
        Spacer(modifier = Modifier.width(20.dp))
        Button(
          modifier = Modifier.weight(1f),
          onClick = onForkClick,
        ) {
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
}

@PreviewDemo
@Composable
private fun DetailListPreview() {
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
  DetailList(model)
}
