setupModule(Module.Common)

dependencies {
  implementation(
    // network
    Libs.coil,
    Libs.okHttpLogInterceptor,
    Libs.okHttpBrotliInterceptor
  )
}
