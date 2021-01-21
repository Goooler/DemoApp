setupModule(Module.Map) {
  buildTypes {
    getByName("release") {
      resValue("string", "amap_key", "419ff22368500a49f0d894ac80d08725")
    }
    getByName("debug") {
      resValue("string", "amap_key", "ab0ee784fe95f3de2af3db07e11c37ed")
    }
  }
}

dependencies {
  implementations(*Libs.amap)
}