plugins {
  id(libs.plugins.android.application.get().pluginId)
  id(libs.plugins.kotlin.android.get().pluginId)
  id(libs.plugins.kotlin.kapt.get().pluginId)
}

setupApp(AppModule.Obsolete)

dependencies {
  implementation(libs.square.retrofit.gson)
  implementation(libs.square.okHttp.logInterceptor)
  implementation(libs.fastjson)
  implementation(libs.google.gson)
  implementation(libs.bundles.glide)
}
