plugins {
  id(Plugins.androidApplication)
}

setupApp("$appPackageName.test", "test") {
  defaultConfig {
    testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
  }
}

dependencies {
  implementation(
    *Libs.glide,
    Libs.fastjson,
    *Libs.gson,
    Libs.mmkv,
    *Libs.objectBox,
    *Libs.amap
  )
  testImplementation(
    "junit:junit:4.13.1",
    "org.robolectric:robolectric:4.4"
  )
  androidTestImplementation(
    "androidx.test.ext:junit:1.1.2",
    "androidx.test.espresso:espresso-core:3.3.0"
  )
}
