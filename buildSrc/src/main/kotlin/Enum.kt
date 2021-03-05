@file:Suppress("SpellCheckingInspection")

enum class Module(val tag: String, val runAlone: Boolean = false) {
  //---------------------base-------------------------------//
  Base("base"),
  Common("common"),

  //---------------------biz-------------------------------//
  Compose("compose"),
  Login("login"),
  Main("main"),
  Map("map"),
  Web("web"),

  //---------------------func-------------------------------//
  Adapter("adapter"),
  Widget("widget"),

  //---------------------app-------------------------------//
  App("app"),
  Test("test")
}

enum class Flavor(val flavor: String) {
  Daily("daily"),
  Online("online")
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

enum class GradleTask(val task: String) {
  Clean("clean"),
  DependencyUpdate("dependencyUpdates"),
  LintKotlin("lintKotlin"),
  FormatKotlin("formatKotlin")
}