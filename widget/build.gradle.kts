import com.android.build.gradle.LibraryExtension

applyPlugins(Plugins.androidLibrary)

setupBase<LibraryExtension>(Module.Widget)

dependencies {
  implementations(Libs.core)
}
