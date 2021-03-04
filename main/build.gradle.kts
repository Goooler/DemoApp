setupModule(Module.Main)

dependencies {
  implementations(
    project(Module.Adapter.moduleName),
    project(Module.Widget.moduleName),
    Libs.coordinatorLayout
  )
}
