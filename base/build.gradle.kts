import com.android.build.gradle.LibraryExtension

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
