enum class Module(val tag: String, val runAlone: Boolean = false) {
    Base("base"),
    Common("common"),
    Login("login"),
    Main("main"),
    Adapter("adapter"),
    Widget("widget"),
    Web("web")
}

enum class Flavor(val tag: String) {
    Daily("daily"),
    Online("online")
}

enum class BuildConfigField(val tag: String) {
    VersionName("VERSION_NAME"),
    VersionCode("VERSION_CODE"),
    CdnPrefix("CDN_PREFIX"),
    ApiHost("API_HOST")
}