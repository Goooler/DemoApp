plugins {
  id(Plugins.androidApplication)
  id(Plugins.kotlinAndroid)
  id(Plugins.kotlinKapt)
  id(Plugins.hilt)
}

setupApp(AppModule.App)

dependencies {
  implementations(
    // projects
    projects.login,
    projects.main,
    projects.web,
  )
  debugImplementation(Libs.leakCanary)
  implementation(Libs.hilt)
  kapt(Libs.hiltCompiler)
}
