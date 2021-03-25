import com.android.build.gradle.LibraryExtension

applyPlugins(Plugins.androidLibrary)

setupBase<LibraryExtension>()

dependencies {
  apis(
    // architecture
    *Libs.coroutines,
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
