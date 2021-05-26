setupApp("$appPackageName.test", "test") {
  defaultConfig {
    testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
  }
}

dependencies {
  implementations(
    *Libs.glide,
    Libs.fastjson,
    *Libs.gson,
    Libs.okHttpLogInterceptor
  )
}
