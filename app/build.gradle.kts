plugins {
  id(Plugins.androidApplication)
  id(Plugins.kotlinAndroid)
  id(Plugins.kotlinKapt)
}

setupApp(AppModule.App)

dependencies {
  implementations(
    // projects
    projects.login,
    projects.main,
    projects.web,
  )
  debugImplementations(Libs.leakCanary)
}
