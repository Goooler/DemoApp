@file:Suppress("SpellCheckingInspection")

interface Module {
  val tag: String
}

enum class LibModule(override val tag: String) : Module {
  //---------------------base-------------------------------//
  Base("base"),
  Common("common"),

  //---------------------biz-------------------------------//
  Login("login"),
  Main("main"),
  Web("web"),

  //---------------------func-------------------------------//
  Adapter("adapter"),
}

enum class AppModule(override val tag: String, val appName: String, val packageName: String) :
  Module {
  App("app", "io.goooler.demoapp", "Demo"),
  Test("app", "io.goooler.test", "Test")
}

enum class Flavor {
  Daily,
  Online
}

enum class BuildConfigField(val tag: String) {
  VersionCode("VERSION_CODE"),
  VersionName("VERSION_NAME"),
  CdnPrefix("CDN_PREFIX"),
  ApiHost("API_HOST"),
  DoraemonKitKey("DORAEMON_KIT_KEY")
}

enum class ApiKey(val key: String) {
  DoraemonKit("4a8c3eef29f029bc197705faad83f43d"),
  AmapDebug("419ff22368500a49f0d894ac80d08725"),
  AmapRelease("ab0ee784fe95f3de2af3db07e11c37ed")
}
