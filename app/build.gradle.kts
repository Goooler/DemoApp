setupApp(appPackageName, appName)

dependencies {
  implementations(
    // projects
    project(LibModule.Login.moduleName),
    project(LibModule.Main.moduleName),
    project(LibModule.Web.moduleName)
  )
}
