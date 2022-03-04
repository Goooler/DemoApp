@file:Suppress("SpellCheckingInspection")

private const val retrofitVersion = "2.9.0"
private const val moshiVersion = "1.13.0"
private const val srlVersion = "2.0.3"
private const val glideVersion = "4.13.1"
private const val coilVersion = "1.4.0"
private const val roomVersion = "2.4.2"
private const val hiltVersion = "2.41"
private const val chuckerVersion = "3.5.2"

const val appCompatVersion = "1.4.1"
const val coreVersion = "1.7.0"
const val activityVersion = "1.4.0"
const val collectionVersion = "1.2.0"
const val fragmentVersion = "1.4.0"
const val lifecycleVersion = "2.4.1"
const val supportVersion = "28.0.0"
const val coroutinesVersion = "1.6.0"
const val okHttpVersion = "4.9.3"
const val ktlintVersion = "0.44.0"

object Plugins {
  const val kotlinAndroid = "kotlin-android"
  const val kotlinKapt = "kotlin-kapt"
  const val androidLibrary = "com.android.library"
  const val androidApplication = "com.android.application"
  const val hilt = "com.google.dagger.hilt.android"
  const val ktlint = "org.jlleitschuh.gradle.ktlint"
  const val moshiX = "dev.zacsweers.moshix"
}

object Libs {
  const val coroutines = "org.jetbrains.kotlinx:kotlinx-coroutines-android:$coroutinesVersion"
  const val core = "androidx.core:core-ktx:$coreVersion"
  const val appCompat = "androidx.appcompat:appcompat:$appCompatVersion"
  const val annotation = "androidx.annotation:annotation:1.3.0"
  const val activity = "androidx.activity:activity-ktx:$activityVersion"
  const val fragment = "androidx.fragment:fragment-ktx:$fragmentVersion"
  const val constraintLayout = "androidx.constraintlayout:constraintlayout:2.1.3"
  const val coordinatorLayout = "androidx.coordinatorlayout:coordinatorlayout:1.2.0"
  const val material = "com.google.android.material:material:1.5.0"
  const val recyclerView = "androidx.recyclerview:recyclerview:1.2.1"
  const val cardView = "androidx.cardview:cardview:1.0.0"
  const val collection = "androidx.collection:collection-ktx:$collectionVersion"
  const val fastjson = "com.alibaba:fastjson:1.1.72.android"
  const val leakCanary = "com.squareup.leakcanary:leakcanary-android:2.8.1"
  const val flycoTabLayout = "com.flyco.tablayout:FlycoTabLayout_Lib:2.2.0"
  const val utils = "com.blankj:utilcodex:1.31.0"
  const val okHttp = "com.squareup.okhttp3:okhttp:$okHttpVersion"
  const val okHttpLogInterceptor = "com.squareup.okhttp3:logging-interceptor:$okHttpVersion"
  const val retrofit = "com.squareup.retrofit2:retrofit:$retrofitVersion"
  const val retrofitMoshiConverter = "com.squareup.retrofit2:converter-moshi:$retrofitVersion"
  const val moshi = "com.squareup.moshi:moshi:$moshiVersion"
  const val paging = "androidx.paging:paging-runtime:3.1.0"
  const val viewPager2 = "androidx.viewpager2:viewpager2:1.0.0"
  const val photoView = "com.github.chrisbanes:PhotoView:2.3.0"
  const val browser = "androidx.browser:browser:1.4.0"
  const val webKit = "androidx.webkit:webkit:1.4.0"
  const val chuckerDebug = "com.github.chuckerteam.chucker:library:$chuckerVersion"
  const val chuckerRelease = "com.github.chuckerteam.chucker:library-no-op:$chuckerVersion"
  const val hilt = "com.google.dagger:hilt-android:$hiltVersion"

  const val hiltPlugin = "com.google.dagger:hilt-android-gradle-plugin:$hiltVersion"
  const val ktlintPlugin = "org.jlleitschuh.gradle:ktlint-gradle:10.2.1"
  const val moshiX = "dev.zacsweers.moshix:dev.zacsweers.moshix.gradle.plugin:0.17.0"

  const val roomCompiler = "androidx.room:room-compiler:$roomVersion"
  const val hiltCompiler = "com.google.dagger:hilt-android-compiler:$hiltVersion"

  val robolectric = arrayOf(
    "org.robolectric:robolectric:4.7.3"
  )
  val androidTests = arrayOf(
    "androidx.test.ext:junit:1.1.3",
    "androidx.test.espresso:espresso-core:3.4.0"
  )
  val lifecycle = arrayOf(
    "androidx.lifecycle:lifecycle-viewmodel-ktx:$lifecycleVersion",
    "androidx.lifecycle:lifecycle-livedata-ktx:$lifecycleVersion",
    "androidx.lifecycle:lifecycle-service:$lifecycleVersion"
  )
  val room = arrayOf(
    "androidx.room:room-runtime:$roomVersion",
    "androidx.room:room-ktx:$roomVersion"
  )
  val smartRefreshLayout = arrayOf(
    "com.scwang.smart:refresh-layout-kernel:$srlVersion",
    "com.scwang.smart:refresh-header-classics:$srlVersion",
    "com.scwang.smart:refresh-footer-classics:$srlVersion"
  )
  val gson = arrayOf(
    "com.google.code.gson:gson:2.9.0",
    "com.squareup.retrofit2:converter-gson:$retrofitVersion"
  )
  val glide = arrayOf(
    "com.github.bumptech.glide:glide:$glideVersion",
    "com.github.bumptech.glide:okhttp3-integration:$glideVersion",
    "jp.wasabeef:glide-transformations:4.3.0"
  )
  val coil = arrayOf(
    "io.coil-kt:coil:$coilVersion",
    "io.coil-kt:coil-gif:$coilVersion",
    "io.coil-kt:coil-svg:$coilVersion"
  )
  val rx = arrayOf(
    "io.reactivex.rxjava3:rxjava:3.1.3",
    "io.reactivex.rxjava3:rxandroid:3.0.0",
    "com.squareup.retrofit2:adapter-rxjava3:$retrofitVersion"
  )
}
