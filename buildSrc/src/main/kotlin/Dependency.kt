@file:Suppress("SpellCheckingInspection")

private const val coreVersion = "1.3.2"
private const val appCompatVersion = "1.2.0"
private const val supportVersion = "1.0.0"
private const val constraintLayoutVersion = "2.0.4"
private const val coordinatorLayoutVersion = "1.1.0"
private const val fragmentVersion = "1.3.2"
private const val activityVersion = "1.2.2"
private const val materialVersion = "1.3.0"
private const val flexBoxVersion = "2.0.1"
private const val cardViewVersion = "1.0.0"
private const val recyclerViewVersion = "1.1.0"
private const val annotationVersion = "1.2.0"
private const val lifecycleVersion = "2.3.1"
private const val navigationVersion = "2.3.1"
private const val workVersion = "2.4.0"
private const val preferenceVersion = "1.1.1"
private const val okHttpVersion = "4.9.1"
private const val retrofitVersion = "2.9.0"
private const val fastjsonVersion = "1.1.72.android"
private const val arouterVersion = "1.5.1"
private const val gsonVersion = "2.8.6"
private const val moshiVersion = "1.11.0"
private const val rxAndroidVersion = "3.0.0"
private const val rxJavaVersion = "3.0.11"
private const val mpChartVersion = "v3.1.0"
private const val lottieVersion = "3.5.0"
private const val smartRefreshLayoutVersion = "2.0.3"
private const val glideVersion = "4.12.0"
private const val glideTransformationVersion = "4.3.0"
private const val leakCanaryVersion = "2.6"
private const val lubanVersion = "1.1.8"
private const val mmkvVersion = "1.2.7"
private const val coroutinesVersion = "1.4.3"
private const val desugarVersion = "1.1.5"
private const val objectBoxVersion = "2.9.1"
private const val protocVersion = "3.15.4"
private const val tbsVersion = "43938"
private const val baseRvHelperVersion = "3.0.4"
private const val flycoTabLayoutVersion = "1.3.3"
private const val coilVersion = "1.1.1"
private const val coilTransformationVersion = "1.0.0"
private const val utilsVersion = "1.30.6"
private const val pagingVersion = "3.0.0-beta03"
private const val viewPager2Version = "1.0.0"
private const val protobufVersion = "0.8.15"
private const val photoViewVersion = "2.3.0"
private const val permissionXVersion = "1.4.0"
private const val arouterRegisterVersion = "1.0.2"
private const val roomVersion = "2.2.6"
private const val hiltVersion = "2.33-beta"
private const val hiltVmVersion = "1.0.0-alpha03"
private const val dataStoreVersion = "1.0.0-alpha07"
private const val collectionVersion = "1.1.0"
private const val browserVersion = "1.3.0"
private const val webKitVersion = "1.4.0"
private const val doraemonKitVersion = "3.3.5"
private const val chuckerVersion = "3.4.0"
private const val dependencyUpdateVersion = "0.38.0"
private const val kotlinterVersion = "3.4.0"
private const val detektVersino = "1.16.0"
private const val accompanistVersion = "0.6.2"
const val composeVersion = "1.0.0-beta02"

object Plugins {
  const val kotlinAndroid = "kotlin-android"
  const val kotlinKapt = "kotlin-kapt"
  const val kotlinParcelize = "kotlin-parcelize"
  const val androidLibrary = "com.android.library"
  const val androidApplication = "com.android.application"
  const val hilt = "dagger.hilt.android.plugin"
  const val arouter = "com.alibaba.arouter"
  const val protobuf = "com.google.protobuf"
  const val dependencyUpdate = "com.github.ben-manes.versions"
  const val kotlinter = "org.jmailen.kotlinter"
  const val detekt = "io.gitlab.arturbosch.detekt"
}

object Libs {
  const val core = "androidx.core:core-ktx:$coreVersion"
  const val desugar = "com.android.tools:desugar_jdk_libs:$desugarVersion"
  const val appCompat = "androidx.appcompat:appcompat:$appCompatVersion"
  const val annotation = "androidx.annotation:annotation:$annotationVersion"
  const val activity = "androidx.activity:activity-ktx:$activityVersion"
  const val fragment = "androidx.fragment:fragment-ktx:$fragmentVersion"
  const val legacySupport = "androidx.legacy:legacy-support-v4:$supportVersion"
  const val preference = "androidx.preference:preference:$preferenceVersion"
  const val palette = "androidx.palette:palette-ktx:$cardViewVersion"
  const val constraintLayout = "androidx.constraintlayout:constraintlayout:$constraintLayoutVersion"
  const val coordinatorLayout =
    "androidx.coordinatorlayout:coordinatorlayout:$coordinatorLayoutVersion"
  const val material = "com.google.android.material:material:$materialVersion"
  const val recyclerView = "androidx.recyclerview:recyclerview:$recyclerViewVersion"
  const val workManager = "androidx.work:work-runtime-ktx:$workVersion"
  const val flexBox = "com.google.android:flexbox:$flexBoxVersion"
  const val cardView = "androidx.cardview:cardview:$cardViewVersion"
  const val collection = "androidx.collection:collection-ktx:$collectionVersion"
  const val mpChart = "com.github.PhilJay:MPAndroidChart:$mpChartVersion"
  const val lottie = "com.airbnb.android:lottie:$lottieVersion"
  const val fastjson = "com.alibaba:fastjson:$fastjsonVersion"
  const val arouter = "com.alibaba:arouter-api:$arouterVersion"
  const val luban = "top.zibin:Luban:$lubanVersion"
  const val leakCanary = "com.squareup.leakcanary:leakcanary-android:$leakCanaryVersion"
  const val mmkv = "com.tencent:mmkv-static:$mmkvVersion"
  const val protoc = "com.google.protobuf:protoc:$protocVersion"
  const val protobufLite = "com.google.protobuf:protobuf-javalite:$protocVersion"
  const val tbs = "com.tencent.tbs.tbssdk:sdk:$tbsVersion"
  const val baseRvHelper = "com.github.CymChad:BaseRecyclerViewAdapterHelper:$baseRvHelperVersion"
  const val flycoTabLayout = "com.lzp:FlycoTabLayoutZ:lastversion:$flycoTabLayoutVersion"
  const val utils = "com.blankj:utilcodex:$utilsVersion"
  const val okHttp = "com.squareup.okhttp3:okhttp:$okHttpVersion"
  const val okHttpLogInterceptor = "com.squareup.okhttp3:logging-interceptor:$okHttpVersion"
  const val okHttpBrotliInterceptor = "com.squareup.okhttp3:okhttp-brotli:$okHttpVersion"
  const val retrofit = "com.squareup.retrofit2:retrofit:$retrofitVersion"
  const val paging = "androidx.paging:paging-runtime:$pagingVersion"
  const val viewPager2 = "androidx.viewpager2:viewpager2:$viewPager2Version"
  const val photoView = "com.github.chrisbanes:PhotoView:$photoViewVersion"
  const val permissionX = "com.permissionx.guolindev:permissionx:$permissionXVersion"
  const val browser = "androidx.browser:browser:$browserVersion"
  const val webKit = "androidx.webkit:webkit:$webKitVersion"
  const val doraemonKitDebug = "com.didichuxing.doraemonkit:dokitx:$doraemonKitVersion"
  const val doraemonKitRelease = "com.didichuxing.doraemonkit:dokitx-no-op:$doraemonKitVersion"
  const val chuckerDebug = "com.github.chuckerteam.chucker:library:$chuckerVersion"
  const val chuckerRelease = "com.github.chuckerteam.chucker:library-no-op:$chuckerVersion"
  const val accompanist = "dev.chrisbanes.accompanist:accompanist-insets:$accompanistVersion"

  const val protobufPlugin = "com.google.protobuf:protobuf-gradle-plugin:$protobufVersion"
  const val arouterPlugin = "com.alibaba:arouter-register:$arouterRegisterVersion"
  const val hiltPlugin = "com.google.dagger:hilt-android-gradle-plugin:$hiltVersion"
  const val kotlinterPlugin = "org.jmailen.gradle:kotlinter-gradle:$kotlinterVersion"
  const val dependencyUpdatesPlugin =
    "com.github.ben-manes:gradle-versions-plugin:$dependencyUpdateVersion"
  const val detektPlugin = "io.gitlab.arturbosch.detekt:detekt-gradle-plugin:$detektVersino"

  const val objectBoxKapt = "io.objectbox:objectbox-processor:$objectBoxVersion"
  const val roomKapt = "androidx.room:room-compiler:$roomVersion"
  const val arouterKapt = "com.alibaba:arouter-compiler:$arouterVersion"
  const val moshiKapt = "com.squareup.moshi:moshi-kotlin-codegen:$moshiVersion"
  val hiltKapt = arrayOf(
    "com.google.dagger:hilt-android-compiler:$hiltVersion",
    "androidx.hilt:hilt-compiler:$hiltVmVersion"
  )

  val coroutines = arrayOf(
    "org.jetbrains.kotlinx:kotlinx-coroutines-core:$coroutinesVersion",
    "org.jetbrains.kotlinx:kotlinx-coroutines-android:$coroutinesVersion"
  )
  val compose = arrayOf(
    "androidx.activity:activity-compose:1.3.0-alpha04",
    "androidx.lifecycle:lifecycle-viewmodel-compose:1.0.0-alpha02",
    "androidx.compose.foundation:foundation:$composeVersion",
    "androidx.compose.foundation:foundation-layout:$composeVersion",
    "androidx.compose.material:material:$composeVersion",
    "androidx.compose.ui:ui:$composeVersion",
    "androidx.compose.ui:ui-tooling:$composeVersion",
    "androidx.compose.ui:ui-util:$composeVersion"
  )
  val lifecycle = arrayOf(
    "androidx.lifecycle:lifecycle-viewmodel-ktx:$lifecycleVersion",
    "androidx.lifecycle:lifecycle-livedata-ktx:$lifecycleVersion",
    "androidx.lifecycle:lifecycle-runtime-ktx:$lifecycleVersion",
    "androidx.lifecycle:lifecycle-common-java8:$lifecycleVersion"
  )
  val room = arrayOf(
    "androidx.room:room-runtime:$roomVersion",
    "androidx.room:room-ktx:$roomVersion"
  )
  val hilt = arrayOf(
    "com.google.dagger:hilt-android:$hiltVersion",
    "androidx.hilt:hilt-lifecycle-viewmodel:$hiltVmVersion"
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
    "com.google.code.gson:gson:$gsonVersion",
    "com.squareup.retrofit2:converter-gson:$retrofitVersion"
  )
  val moshi = arrayOf(
    "com.squareup.moshi:moshi:$moshiVersion",
    "com.squareup.retrofit2:converter-moshi:$retrofitVersion"
  )
  val glide = arrayOf(
    "com.github.bumptech.glide:glide:$glideVersion",
    "com.github.bumptech.glide:okhttp3-integration:$glideVersion",
    "jp.wasabeef:glide-transformations:$glideTransformationVersion"
  )
  val coil = arrayOf(
    "io.coil-kt:coil:$coilVersion",
    "io.coil-kt:coil-gif:$coilVersion",
    "io.coil-kt:coil-svg:$coilVersion",
    "com.github.Commit451.coil-transformations:transformations:$coilTransformationVersion"
  )
  val objectBox = arrayOf(
    "io.objectbox:objectbox-android:$objectBoxVersion",
    "io.objectbox:objectbox-kotlin:$objectBoxVersion"
  )
  val rx = arrayOf(
    "io.reactivex.rxjava3:rxjava:$rxJavaVersion",
    "io.reactivex.rxjava3:rxandroid:$rxAndroidVersion",
    "com.squareup.retrofit2:adapter-rxjava3:$retrofitVersion"
  )
  val amap = arrayOf(
    //"com.amap.api:map2d:latest.integration",
    "com.amap.api:navi-3dmap:latest.integration",
    "com.amap.api:search:latest.integration",
    "com.amap.api:location:latest.integration"
  )
}