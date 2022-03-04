plugins {
  id(Plugins.androidApplication)
  id(Plugins.kotlinAndroid)
}

setupApp(AppModule.Test)

dependencies {
  implementations(
    *Libs.glide,
    Libs.fastjson,
    *Libs.gson,
    Libs.okHttpLogInterceptor
  )
}
