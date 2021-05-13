setupLib(Module.Map) {
  buildTypes {
    release {
      resValue("string", "amap_key", ApiKey.AmapRelease.key)
    }
    debug {
      resValue("string", "amap_key", ApiKey.AmapDebug.key)
    }
  }
}

dependencies {
  implementations(*Libs.amap)
}
