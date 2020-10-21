plugins {
    id(Plugins.androidLibrary)
    id(Plugins.kotlinAndroid)
    id(Plugins.kotlinKapt)
}

setupCommon(Module.Login)

dependencies {
    implementation(project(getModuleName(Module.Common)))
}