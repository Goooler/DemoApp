setupModule(Module.Common)

dependencies {
  implementations(
    // network
    Libs.coil,
    Libs.okHttpLogInterceptor,
    Libs.okHttpBrotliInterceptor
  )
  debugImplementations(
    Libs.leakCanary,
    Libs.doraemonKitDebug
  )
  releaseImplementations(Libs.doraemonKitRelease)
}
