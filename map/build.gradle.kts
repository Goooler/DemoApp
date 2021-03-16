setupLib(Module.Map) {
  buildTypes {
    getByName("release") {
      resValue("string", "amap_key", ApiKey.AmapRelease.key)
    }
    getByName("debug") {
      resValue("string", "amap_key", ApiKey.AmapDebug.key)
    }
  }
}

dependencies {
  implementations(*Libs.amap)
}