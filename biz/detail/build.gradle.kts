plugins {
  alias(libs.plugins.android.library)
  alias(libs.plugins.ksp)
}

android {
  buildFeatures.compose = true
  composeOptions.kotlinCompilerExtensionVersion = libs.versions.androidX.composeCompiler.get()
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
