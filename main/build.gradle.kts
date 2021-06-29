setupLib(Module.Main) {
  defaultConfig.testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"

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
