import com.android.build.gradle.LibraryExtension

setupBase<LibraryExtension>(Module.Widget)

dependencies {
  implementations(Libs.core)
}
