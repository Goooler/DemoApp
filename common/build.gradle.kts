plugins {
  id(Plugins.androidLibrary)
}

setupModule(Module.Common)

dependencies {
  implementation(
    // network
    Libs.coil,
    Libs.okHttpLogInterceptor
  )
}
