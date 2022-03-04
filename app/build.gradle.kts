plugins {
  id(Plugins.androidApplication)
  id(Plugins.kotlinAndroid)
  id(Plugins.kotlinKapt)
  id(Plugins.hilt)
}

setupApp(AppModule.App)

dependencies {
  implementation(projects.login)
  implementation(projects.main)
  implementation(projects.web)

  implementation(Libs.hilt)
  kapt(Libs.hiltCompiler)

  debugImplementation(Libs.leakCanary)
}
