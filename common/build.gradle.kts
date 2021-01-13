setupModule(Module.Common)

dependencies {
  implementation(
    // network
    Libs.coil,
    Libs.okHttpLogInterceptor,
    Libs.okHttpBrotliInterceptor
  )
  debugImplementation(
    Libs.leakCanary,
    Libs.doraemonKitDebug
  )
  releaseImplementation(Libs.doraemonKitRelease)
}
