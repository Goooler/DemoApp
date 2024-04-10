plugins {
  alias(libs.plugins.android.library)
  alias(libs.plugins.kotlin.compose)
  alias(libs.plugins.ksp)
}

android {
  buildFeatures.compose = true
}

composeCompiler {
  suppressKotlinVersionCompatibilityCheck = libs.versions.kotlin.get()
}

dependencies {
  implementation(projects.common)

  implementation(libs.androidX.activity.compose)
  implementation(platform(libs.androidX.compose.bom))
  implementation(libs.bundles.androidX.compose)
  debugImplementation(libs.androidX.compose.tooling)

  implementation(libs.square.moshi)
  ksp(libs.square.moshi.compiler)
}
