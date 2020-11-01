plugins {
    id(Plugins.androidLibrary)
}

setupCommon(Module.Login)

dependencies {
    implementation(project(getModuleName(Module.Common)))
}