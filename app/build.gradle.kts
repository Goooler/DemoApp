plugins {
    id(Plugins.androidApplication)
}

setupApp(appPackageName, appName)

dependencies {
    implementation(
        // projects
        project(getModuleName(Module.Common)),
        project(getModuleName(Module.Login)),
        project(getModuleName(Module.Main)),
        project(getModuleName(Module.Web))
    )

    debugImplementation(Libs.leakCanary)
}