plugins {
    id(Plugins.androidLibrary)
}

setupCommon(Module.Web)

dependencies {
    implementation(project(getModuleName(Module.Common)))
}