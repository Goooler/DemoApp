setupApp("$appPackageName.test", "test") {
  defaultConfig {
    testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
  }
}

dependencies {
  implementations(
    *Libs.glide,
    Libs.fastjson,
    *Libs.gson,
    Libs.mmkv,
    *Libs.objectBox
  )
  testImplementations(
    "junit:junit:4.13.1",
    "org.robolectric:robolectric:4.5"
  )
  androidTestImplementations(
    "androidx.test.ext:junit:1.1.2",
    "androidx.test.espresso:espresso-core:3.3.0"
  )
}
