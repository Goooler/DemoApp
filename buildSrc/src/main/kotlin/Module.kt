internal object ModuleName {
    const val base = "base"
    const val common = "common"
    const val login = "login"
    const val main = "main"
    const val adapter = "adapter"
    const val web = "webview"
}

object Modules {
    const val base = ":${ModuleName.base}"
    const val common = ":${ModuleName.common}"
    const val login = ":${ModuleName.login}"
    const val main = ":${ModuleName.main}"
    const val adapter = ":${ModuleName.adapter}"
    const val web = ":${ModuleName.web}"
}

object ResourcePrefix {
    const val common = "${ModuleName.common}_"
    const val login = "${ModuleName.login}_"
    const val main = "${ModuleName.main}_"
    const val adapter = "${ModuleName.adapter}_"
    const val web = "${ModuleName.web}_"
}

object VersionNameSuffix {
    const val base = "_${ModuleName.base}"
    const val common = "_${ModuleName.common}"
    const val login = "_${ModuleName.login}"
    const val main = "_${ModuleName.main}"
    const val adapter = "_${ModuleName.adapter}"
    const val web = "_${ModuleName.web}"
}