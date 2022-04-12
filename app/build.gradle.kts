plugins {
  alias(libs.plugins.android.application)
  alias(libs.plugins.kotlin.android)
  alias(libs.plugins.kotlin.kapt)
  alias(libs.plugins.hilt)
}

android.kotlinOptions {
  jvmTarget = org.gradle.api.JavaVersion.VERSION_11.toString()
  // https://youtrack.jetbrains.com/issue/KT-41985
  freeCompilerArgs += listOf(
    "-progressive",
    "-opt-in=kotlin.RequiresOptIn",
    "-Xjvm-default=all"
  )
}

dependencies {
  implementation(projects.common)

  implementation(projects.bizLogin)
  implementation(projects.bizMain)
  implementation(projects.bizDetail)
  implementation(projects.bizWeb)

  implementation(libs.androidX.hilt)
  kapt(libs.androidX.hilt.compiler)

  debugImplementation(libs.square.leakCanary)
}
