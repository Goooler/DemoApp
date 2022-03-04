plugins {
  id(Plugins.androidApplication)
  id(Plugins.kotlinAndroid)
  id(Plugins.kotlinKapt)
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
