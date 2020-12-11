enum class Module(val module: String, val runAlone: Boolean = false) {
  //---------------------base-------------------------------//
  Base("base"),
  Common("common"),

  //---------------------biz-------------------------------//
  Login("login"),
  Main("main"),
  Web("web"),

  //---------------------func-------------------------------//
  Adapter("adapter"),
  Widget("widget")
}

enum class Flavor(val flavor: String) {
  Daily("daily"),
  Online("online")
}

enum class BuildConfigField(val tag: String) {
  VersionCode("VERSION_CODE"),
  VersionName("VERSION_NAME"),
  CdnPrefix("CDN_PREFIX"),
  ApiHost("API_HOST")
}

enum class GradleTask(val task: String) {
  Clean("clean"),
  DependencyUpdate("dependencyUpdates")
}