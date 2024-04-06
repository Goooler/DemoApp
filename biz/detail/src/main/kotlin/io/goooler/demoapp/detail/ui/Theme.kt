package io.goooler.demoapp.detail.ui

import android.app.Activity
import android.content.res.Configuration.UI_MODE_NIGHT_YES
import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.calculateEndPadding
import androidx.compose.foundation.layout.calculateStartPadding
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.platform.LocalView
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.LayoutDirection
import androidx.core.view.WindowCompat

private val DarkColorScheme = darkColorScheme(
  surface = Blue,
  onSurface = Navy,
  primary = Navy,
  onPrimary = Chartreuse,
)

private val LightColorScheme = lightColorScheme(
  surface = Blue,
  onSurface = Color.White,
  primary = LightBlue,
  onPrimary = Navy,
)

@Composable
fun DemoTheme(
  darkTheme: Boolean = isSystemInDarkTheme(),
  // Dynamic color is available on Android 12+
  dynamicColor: Boolean = true,
  content: @Composable () -> Unit,
) {
  val colorScheme = when {
    dynamicColor && Build.VERSION.SDK_INT >= Build.VERSION_CODES.S -> {
      val context = LocalContext.current
      if (darkTheme) dynamicDarkColorScheme(context) else dynamicLightColorScheme(context)
    }

    darkTheme -> DarkColorScheme
    else -> LightColorScheme
  }

  val view = LocalView.current
  if (!view.isInEditMode) {
    SideEffect {
      val window = (view.context as Activity).window
      window.statusBarColor = colorScheme.primary.toArgb()
      WindowCompat.getInsetsController(window, view).isAppearanceLightStatusBars = darkTheme
    }
  }

  MaterialTheme(
    colorScheme = colorScheme,
    typography = Typography,
    content = content,
  )
}

@Preview(
  name = "Light",
  showSystemUi = true,
  showBackground = true,
)
@Preview(
  name = "Dark",
  showSystemUi = true,
  showBackground = true,
  uiMode = UI_MODE_NIGHT_YES,
)
annotation class DemoPreview

@Composable
fun DemoScaffold(
  modifier: Modifier = Modifier,
  content: @Composable (PaddingValues) -> Unit,
) {
  DemoTheme {
    Scaffold(
      modifier = modifier.fillMaxSize(),
      content = content,
    )
  }
}

@Composable
fun PaddingValues.copy(
  layoutDirection: LayoutDirection = LocalLayoutDirection.current,
  start: Dp = calculateStartPadding(layoutDirection),
  top: Dp = calculateTopPadding(),
  end: Dp = calculateEndPadding(layoutDirection),
  bottom: Dp = calculateBottomPadding(),
) = PaddingValues(
  start = start,
  top = top,
  end = end,
  bottom = bottom,
)
