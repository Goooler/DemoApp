plugins {
    id(Plugins.androidLibrary)
}

setupCore().run {
    resourcePrefix = getResourcePrefix(Module.Widget)
    defaultConfig.versionNameSuffix = getVersionNameSuffix(Module.Widget)
}

dependencies {
    implementation(Libs.core)
}