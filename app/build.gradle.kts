setupApp(appPackageName, appName)

dependencies {
  implementation(
    // projects
    project(Module.Login.moduleName),
    project(Module.Main.moduleName),
    //project(Module.Map.moduleName),
    project(Module.Web.moduleName)
  )
}
