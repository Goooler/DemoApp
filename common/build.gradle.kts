plugins {
  id(Plugins.androidLibrary)
}

setupModule(Module.Common)

dependencies {
  // network
  implementation(
    Libs.coil,
    Libs.okHttpLogInterceptor
  )
}
