plugins {
  alias(libs.plugins.android.application)
  alias(libs.plugins.kotlin.android)
  alias(libs.plugins.kotlin.kapt)
}

dependencies {
  implementation(projects.common)

  implementation(libs.square.okHttp)
  implementation(libs.square.retrofit.gson)
  implementation(libs.square.okHttp.logInterceptor)
  implementation(libs.fastjson)
  implementation(libs.google.gson)
  implementation(libs.bundles.glide)
  implementation(libs.rxJava3.java)
  implementation(libs.rxJava3.android)
}
