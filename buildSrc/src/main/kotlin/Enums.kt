@file:Suppress("SpellCheckingInspection")

// TODO: Make Module sealed
interface Module {
  val tag: String
  val id: String
}

enum class LibModule(override val tag: String, override val id: String) : Module {
  //---------------------base-------------------------------//
  Base("base", "io.goooler.demoapp.base"),
  Common("common", "io.goooler.demoapp.common"),

  //---------------------biz-------------------------------//
  Login("login", "io.goooler.demoapp.login"),
  Main("main", "io.goooler.demoapp.main"),
  Detail("detail", "io.goooler.demoapp.detail"),
  Web("web", "io.goooler.demoapp.web"),

  //---------------------func-------------------------------//
  Adapter("adapter", "io.goooler.demoapp.adapter")
}

enum class AppModule(override val tag: String, override val id: String, val appName: String) :
  Module {
  App("app", "io.goooler.demoapp", "Demo"),
  Obsolete("app", "io.goooler.demoapp.obsolete", "Test")
}

enum class BuildConfigField(val key: String, val value: Any) {
  VersionCode("VERSION_CODE", appVersionCode),
  VersionName("VERSION_NAME", appVersionName),
  CdnPrefix("CDN_PREFIX", "https://raw.githubusercontent.com/"),
  ApiHost("API_HOST", "https://api.github.com/")
}
