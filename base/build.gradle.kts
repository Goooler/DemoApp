import com.android.build.gradle.LibraryExtension

plugins {
  id(Plugins.androidLibrary)
  id(Plugins.kotlinAndroid)
  id(Plugins.kotlinKapt)
}

setupBase<LibraryExtension>(LibModule.Base)

dependencies {
  apis(
    // architecture
    Libs.coroutines,
    *Libs.lifecycle,
    Libs.core,
    Libs.annotation,

    // UI
    Libs.appCompat,
    Libs.activity,
    Libs.fragment,

    // network
    Libs.okHttp,
    Libs.retrofit
  )
}
