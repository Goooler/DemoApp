setupLib(Module.Main) {
  sourceSets["main"].res.srcDirs(
    "src/main/res/core",
    "src/main/res/other"
  )
}

dependencies {
  implementations(
    project(Module.Widget.moduleName),
    Libs.coordinatorLayout,
    Libs.flycoTabLayout
  )
}
