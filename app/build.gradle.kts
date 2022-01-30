setupApp(AppModule.App)

dependencies {
  implementations(
    // projects
    project(LibModule.Login.moduleName),
    project(LibModule.Main.moduleName),
    project(LibModule.Web.moduleName)
  )
  debugImplementations(Libs.leakCanary)
}
