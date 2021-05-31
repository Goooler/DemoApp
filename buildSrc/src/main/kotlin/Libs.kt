@file:Suppress("SpellCheckingInspection")

private const val lifecycleVersion = "2.3.1"
private const val navigationVersion = "2.3.1"
private const val okHttpVersion = "4.9.1"
private const val retrofitVersion = "2.9.0"
private const val arouterVersion = "1.5.1"
private const val moshiVersion = "1.12.0"
private const val smartRefreshLayoutVersion = "2.0.3"
private const val glideVersion = "4.12.0"
private const val objectBoxVersion = "2.9.1"
private const val protocVersion = "3.15.4"
private const val coilVersion = "1.2.1"
private const val roomVersion = "2.3.0"
private const val hiltVersion = "2.36"
private const val dataStoreVersion = "1.0.0-alpha07"
private const val doraemonKitVersion = "3.3.5"
private const val chuckerVersion = "3.4.0"
private const val accompanistVersion = "0.6.2"
const val composeVersion = "1.0.0-beta05"

object Plugins {
  const val kotlinAndroid = "kotlin-android"
  const val kotlinKapt = "kotlin-kapt"
  const val kotlinParcelize = "kotlin-parcelize"
  const val androidLibrary = "com.android.library"
  const val androidApplication = "com.android.application"
  const val hilt = "dagger.hilt.android.plugin"
  const val arouter = "com.alibaba.arouter"
  const val protobuf = "com.google.protobuf"
  const val kotlinter = "org.jmailen.kotlinter"
  const val detekt = "io.gitlab.arturbosch.detekt"
  const val picCompress = "io.goooler.android.plugin.pic-compress"
  const val customTrans = "io.goooler.android.plugin.custom-trans"
}

object Libs {
  const val coroutines = "org.jetbrains.kotlinx:kotlinx-coroutines-android:1.5.0"
  const val core = "androidx.core:core-ktx:1.5.0"
  const val desugar = "com.android.tools:desugar_jdk_libs:1.1.5"
  const val appCompat = "androidx.appcompat:appcompat:1.3.0"
  const val annotation = "androidx.annotation:annotation:1.2.0"
  const val activity = "androidx.activity:activity-ktx:1.2.3"
  const val fragment = "androidx.fragment:fragment-ktx:1.3.4"
  const val legacySupport = "androidx.legacy:legacy-support-v4:1.0.0"
  const val preference = "androidx.preference:preference:1.1.1"
  const val palette = "androidx.palette:palette-ktx:1.0.0"
  const val constraintLayout = "androidx.constraintlayout:constraintlayout:2.0.4"
  const val coordinatorLayout = "androidx.coordinatorlayout:coordinatorlayout:1.1.0"
  const val material = "com.google.android.material:material:1.3.0"
  const val recyclerView = "androidx.recyclerview:recyclerview:1.2.0"
  const val workManager = "androidx.work:work-runtime-ktx:2.4.0"
  const val flexBox = "com.google.android:flexbox:2.0.1"
  const val cardView = "androidx.cardview:cardview:1.0.0"
  const val collection = "androidx.collection:collection-ktx:1.1.0"
  const val mpChart = "com.github.PhilJay:MPAndroidChart:v3.1.0"
  const val lottie = "com.airbnb.android:lottie:3.5.0"
  const val fastjson = "com.alibaba:fastjson:1.1.72.android"
  const val arouter = "com.alibaba:arouter-api:$arouterVersion"
  const val luban = "top.zibin:Luban:1.1.8"
  const val leakCanary = "com.squareup.leakcanary:leakcanary-android:2.7"
  const val mmkv = "com.tencent:mmkv-static:1.2.8"
  const val protoc = "com.google.protobuf:protoc:$protocVersion"
  const val protobufLite = "com.google.protobuf:protobuf-javalite:$protocVersion"
  const val tbs = "com.tencent.tbs.tbssdk:sdk:43938"
  const val baseRvHelper = "com.github.CymChad:BaseRecyclerViewAdapterHelper:3.0.6"
  const val flycoTabLayout = "com.flyco.tablayout:FlycoTabLayout_Lib:2.2.0"
  const val utils = "com.blankj:utilcodex:1.30.6"
  const val okHttp = "com.squareup.okhttp3:okhttp:$okHttpVersion"
  const val okHttpLogInterceptor = "com.squareup.okhttp3:logging-interceptor:$okHttpVersion"
  const val retrofit = "com.squareup.retrofit2:retrofit:$retrofitVersion"
  const val paging = "androidx.paging:paging-runtime:3.0.0"
  const val viewPager2 = "androidx.viewpager2:viewpager2:1.0.0"
  const val photoView = "com.github.chrisbanes:PhotoView:2.3.0"
  const val permissionX = "com.permissionx.guolindev:permissionx:1.4.0"
  const val browser = "androidx.browser:browser:1.3.0"
  const val webKit = "androidx.webkit:webkit:1.4.0"
  const val doraemonKitDebug = "com.didichuxing.doraemonkit:dokitx:$doraemonKitVersion"
  const val doraemonKitRelease = "com.didichuxing.doraemonkit:dokitx-no-op:$doraemonKitVersion"
  const val chuckerDebug = "com.github.chuckerteam.chucker:library:$chuckerVersion"
  const val chuckerRelease = "com.github.chuckerteam.chucker:library-no-op:$chuckerVersion"

  const val protobufPlugin = "com.google.protobuf:protobuf-gradle-plugin:0.8.15"
  const val arouterPlugin = "com.alibaba:arouter-register:1.0.2"
  const val hiltPlugin = "com.google.dagger:hilt-android-gradle-plugin:$hiltVersion"
  const val kotlinterPlugin = "org.jmailen.gradle:kotlinter-gradle:3.4.4"
  const val detektPlugin = "io.gitlab.arturbosch.detekt:detekt-gradle-plugin:1.17.1"

  const val objectBoxCompiler = "io.objectbox:objectbox-processor:$objectBoxVersion"
  const val roomCompiler = "androidx.room:room-compiler:$roomVersion"
  const val arouterCompiler = "com.alibaba:arouter-compiler:$arouterVersion"
  const val moshiCompiler = "com.squareup.moshi:moshi-kotlin-codegen:$moshiVersion"
  val hiltCompiler = arrayOf(
    "com.google.dagger:hilt-android-compiler:$hiltVersion",
    "androidx.hilt:hilt-compiler:1.0.0"
  )

  val compose = arrayOf(
    "androidx.activity:activity-compose:1.3.0-alpha07",
    "androidx.lifecycle:lifecycle-viewmodel-compose:1.0.0-alpha04",
    "androidx.compose.foundation:foundation:$composeVersion",
    "androidx.compose.foundation:foundation-layout:$composeVersion",
    "androidx.compose.material:material:$composeVersion",
    "androidx.compose.ui:ui:$composeVersion",
    "androidx.compose.ui:ui-tooling:$composeVersion",
    "androidx.compose.ui:ui-util:$composeVersion"
  )
  val accompanist = arrayOf(
    "com.google.accompanist:accompanist-coil:$accompanistVersion",
    "com.google.accompanist:accompanist-insets:$accompanistVersion",
    "com.google.accompanist:accompanist-appcompat-theme:$accompanistVersion",
    "com.google.accompanist:accompanist-systemuicontroller:$accompanistVersion",
    "com.google.accompanist:accompanist-pager:$accompanistVersion"
  )
  val lifecycle = arrayOf(
    "androidx.lifecycle:lifecycle-viewmodel-ktx:$lifecycleVersion",
    "androidx.lifecycle:lifecycle-livedata-ktx:$lifecycleVersion",
    "androidx.lifecycle:lifecycle-service:$lifecycleVersion",
    "androidx.lifecycle:lifecycle-common-java8:$lifecycleVersion"
  )
  val room = arrayOf(
    "androidx.room:room-runtime:$roomVersion",
    "androidx.room:room-ktx:$roomVersion"
  )
  val hilt = arrayOf(
    "com.google.dagger:hilt-android:$hiltVersion",
    "androidx.hilt:hilt-lifecycle-viewmodel:1.0.0-alpha03"
  )
  val dataStore = arrayOf(
    "androidx.datastore:datastore-preferences:$dataStoreVersion",
    "androidx.datastore:datastore-core:$dataStoreVersion"
  )
  val navigation = arrayOf(
    "androidx.navigation:navigation-ui-ktx:$navigationVersion",
    "androidx.navigation:navigation-fragment-ktx:$navigationVersion",
    "androidx.navigation:navigation-common-ktx:$navigationVersion",
    "androidx.navigation:navigation-runtime-ktx:$navigationVersion"
  )
  val smartRefreshLayout = arrayOf(
    "com.scwang.smart:refresh-layout-kernel:$smartRefreshLayoutVersion",
    "com.scwang.smart:refresh-header-classics:$smartRefreshLayoutVersion",
    "com.scwang.smart:refresh-footer-classics:$smartRefreshLayoutVersion"
  )
  val gson = arrayOf(
    "com.google.code.gson:gson:2.8.7",
    "com.squareup.retrofit2:converter-gson:$retrofitVersion"
  )
  val moshi = arrayOf(
    "com.squareup.moshi:moshi:$moshiVersion",
    "com.squareup.retrofit2:converter-moshi:$retrofitVersion"
  )
  val glide = arrayOf(
    "com.github.bumptech.glide:glide:$glideVersion",
    "com.github.bumptech.glide:okhttp3-integration:$glideVersion",
    "jp.wasabeef:glide-transformations:4.3.0"
  )
  val coil = arrayOf(
    "io.coil-kt:coil:$coilVersion",
    "io.coil-kt:coil-gif:$coilVersion",
    "io.coil-kt:coil-svg:$coilVersion",
    "com.github.Commit451.coil-transformations:transformations:1.0.0"
  )
  val objectBox = arrayOf(
    "io.objectbox:objectbox-android:$objectBoxVersion",
    "io.objectbox:objectbox-kotlin:$objectBoxVersion"
  )
  val rx = arrayOf(
    "io.reactivex.rxjava3:rxjava:3.0.12",
    "io.reactivex.rxjava3:rxandroid:3.0.0",
    "com.squareup.retrofit2:adapter-rxjava3:$retrofitVersion"
  )
  val amap = arrayOf(
    //"com.amap.api:map2d:latest.integration",
    "com.amap.api:navi-3dmap:latest.integration",
    "com.amap.api:search:latest.integration",
    "com.amap.api:location:latest.integration"
  )
}
