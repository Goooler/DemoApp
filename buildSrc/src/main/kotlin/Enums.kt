@file:Suppress("SpellCheckingInspection")

// TODO: Make Module sealed
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
  Adapter("adapter")
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

enum class BuildConfigField(val key: String, val value: Any) {
  VersionCode("VERSION_CODE", appVersionCode),
  VersionName("VERSION_NAME", appVersionName),
  CdnPrefix("CDN_PREFIX", "https://raw.githubusercontent.com/"),
  ApiHost("API_HOST", "https://api.github.com/")
}
