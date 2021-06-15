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

  testImplementations(
    "junit:junit:4.13.2",
    "org.robolectric:robolectric:4.5.1"
  )
  androidTestImplementations(
    "androidx.test.ext:junit:1.1.2",
    "androidx.test.espresso:espresso-core:3.3.0"
  )
}
