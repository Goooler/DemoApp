setupApp(AppModule.Test)

dependencies {
  implementations(
    *Libs.glide,
    Libs.fastjson,
    *Libs.gson,
    Libs.okHttpLogInterceptor
  )
}
