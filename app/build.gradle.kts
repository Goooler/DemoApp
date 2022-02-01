setupApp(AppModule.App)

dependencies {
  implementations(
    // projects
    project(LibModule.Login),
    project(LibModule.Main),
    project(LibModule.Web)
  )
  debugImplementations(Libs.leakCanary)
}
