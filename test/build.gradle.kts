plugins {
    id("com.android.library")
    id("kotlin-android")
    id("kotlin-kapt")
}

setupCommon().run {
    defaultConfig {
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }
}

dependencies {
    implementation(project(Modules.base))
    implementation(Libs.fastjson)
    implementation(Libs.gson)

    testImplementation("junit:junit:4.13")
    androidTestImplementation("androidx.test.ext:junit:1.1.2")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.3.0")
}