plugins {
    id(Plugins.androidApplication)
    id(Plugins.kotlinAndroid)
    id(Plugins.kotlinKapt)
}

setupApp(appPackageName, appName)

dependencies {
    implementation(project(getModuleName(Module.Common)))
    implementation(project(getModuleName(Module.Login)))
    implementation(project(getModuleName(Module.Main)))
    implementation(project(getModuleName(Module.Web)))

    debugImplementation(Libs.leakCanary)
}