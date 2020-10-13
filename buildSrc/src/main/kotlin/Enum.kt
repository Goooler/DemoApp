enum class Module(val tag: String, val runAlone: Boolean = false) {
    Base("base"),
    Common("common"),
    Login("login"),
    Main("main"),
    Adapter("adapter"),
    Web("webview")
}

enum class Flavor(val tag: String) {
    Daily("daily"),
    Online("online")
}