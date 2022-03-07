plugins {
  id(libs.plugins.android.application.get().pluginId)
  id(libs.plugins.kotlin.android.get().pluginId)
  id(libs.plugins.kotlin.kapt.get().pluginId)
  id(libs.plugins.hilt.get().pluginId)
}

setupApp(AppModule.App)

dependencies {
  implementation(projects.login)
  implementation(projects.main)
  implementation(projects.web)

  implementation(libs.androidX.hilt)
  kapt(libs.androidX.hilt.compiler)

  debugImplementation(libs.square.leakCanary)
}
