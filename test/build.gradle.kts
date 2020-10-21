plugins {
    id(Plugins.androidApplication)
    id(Plugins.kotlinAndroid)
    id(Plugins.kotlinKapt)
}

setupCommon().run {
    defaultConfig {
        applicationId = "${appPackageName}.test"
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }
}

dependencies {
    implementation(project(getModuleName(Module.Base)))
    implementation(Libs.fastjson)
    implementation(Libs.gson)
    implementation(Libs.mmkv)
    implementation(Libs.objectBox)

    testImplementation("junit:junit:4.13.1")
    androidTestImplementation("androidx.test.ext:junit:1.1.2")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.3.0")
}