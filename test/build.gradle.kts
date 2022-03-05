plugins {
  id(Plugins.androidApplication)
  id(Plugins.kotlinAndroid)
  id(Plugins.kotlinKapt)
}

setupApp(AppModule.Test)

dependencies {
  implementation(libs.square.retrofit.gson)
  implementation(libs.square.okHttp.logInterceptor)
  implementation(libs.fastjson)
  implementation(libs.google.gson)
  implementation(libs.bundles.glide)
}
