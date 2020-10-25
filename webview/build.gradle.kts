plugins {
    id(Plugins.androidLibrary)
    id(Plugins.kotlinAndroid)
    id(Plugins.kotlinKapt)
}

setupCommon(Module.Web)

dependencies {
    implementation(project(getModuleName(Module.Common)))
}