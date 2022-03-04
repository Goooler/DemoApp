plugins {
  id(Plugins.androidApplication)
  id(Plugins.kotlinAndroid)
  id(Plugins.kotlinKapt)
}

setupApp(AppModule.Test)

dependencies {
  implementation(libs.bundles.glide)
  implementation(libs.fastjson)
  implementation(libs.google.gson)
  implementation(libs.retrofit.gsonConverter)
  implementation(libs.okHttp.logInterceptor)
}
