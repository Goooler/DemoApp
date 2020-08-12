@file:Suppress("unused")

import org.gradle.api.JavaVersion

// repo
const val aliyunMaven = "https://maven.aliyun.com/repository/public"
const val jitpackMaven = "https://jitpack.io"

// sdk
const val appTargetSdk = 29
const val appBuildTool = "30.0.1"
const val appMinSdk = 23

// app
const val appVersionName = "1.0"
const val appPackageName = "io.goooler.demoapp"
const val appName = "Demo"

// app dependencies
const val coreVersion = "1.3.0"
const val appCompatVersion = "1.2.0"
const val supportVersion = "1.0.0"
const val constraintLayoutVersion = "1.1.3"
const val fragmentVersion = "1.2.5"
const val materialVersion = "1.1.0"
const val flexBoxVersion = "2.0.1"
const val cardViewVersion = "1.0.0"
const val recyclerViewVersion = "1.1.0"
const val annotationVersion = "1.1.0"
const val lifecycleVersion = "2.2.0"
const val navigationVersion = "2.3.0"
const val workVersion = "2.3.4"
const val preferenceVersion = "1.1.1"
const val multiDexVersion = "2.0.1"
const val okHttpVersion = "4.8.0"
const val retrofitVersion = "2.9.0"
const val fastjsonVersion = "1.1.71.android"
const val arouterVersion = "1.5.0"
const val arouterCompilerVersion = "1.2.2"
const val gsonVersion = "2.8.6"
const val moshiVersion = "1.9.2"
const val eventBusVersion = "3.2.0"
const val liveDataBusVersion = "1.6.1"
const val rxAndroidVersion = "3.0.0"
const val rxJavaVersion = "3.0.5"
const val rxPermissionVersion = "0.12"
const val mpChartVersion = "v3.1.0"
const val lottieVersion = "3.0.1"
const val smartRefreshLayoutVersion = "2.0.1"
const val glideVersion = "4.11.0"
const val glideTransformVersion = "4.1.0"
const val leakCanaryVersion = "2.4"
const val lubanVersion = "1.1.8"
const val mmkvVersion = "1.2.2"
const val coroutinesVersion = "1.3.7"
const val desugarVersion = "1.0.10"
const val objectBoxVersion = "2.7.0"
const val protocVersion = "3.12.4"
const val tbsVersion = "43939"
const val baseRvHelperVersion = "3.0.4"
const val flycoTabLayoutVersion = "2.1.4.2"
const val coilVersion = "0.11.0"
const val utilsVersion = "1.29.0"

// buildScript plugin
const val gradleVersion = "4.0.1"
const val kotlinVersion = "1.3.72"
const val protobufVersion = "0.8.12"
val javaVersion = JavaVersion.VERSION_1_8

object Libs {
    const val gradlePlugin = "com.android.tools.build:gradle:$gradleVersion"
    const val kotlinPlugin = "org.jetbrains.kotlin:kotlin-gradle-plugin:$kotlinVersion"
    const val protobufPlugin = "com.google.protobuf:protobuf-gradle-plugin:$protobufVersion"

    const val core = "androidx.core:core-ktx:$coreVersion"
    const val desugar = "com.android.tools:desugar_jdk_libs:$desugarVersion"
    const val appCompat = "androidx.appcompat:appcompat:$appCompatVersion"
    const val annotation = "androidx.annotation:annotation:$annotationVersion"
    const val multiDex = "androidx.multidex:multidex:$multiDexVersion"
    const val fragment = "androidx.fragment:fragment-ktx:$fragmentVersion"
    const val legacySupport = "androidx.legacy:legacy-support-v4:$supportVersion"
    const val preference = "androidx.preference:preference:$preferenceVersion"
    const val palette = "androidx.palette:palette-ktx:$cardViewVersion"
    const val constraintLayout =
        "androidx.constraintlayout:constraintlayout:$constraintLayoutVersion"
    const val material = "com.google.android.material:material:$materialVersion"
    const val recyclerView = "androidx.recyclerview:recyclerview:$recyclerViewVersion"
    const val workManager = "androidx.work:work-runtime-ktx:$workVersion"
    const val flexBox = "com.google.android:flexbox:$flexBoxVersion"
    const val cardView = "androidx.cardview:cardview:$cardViewVersion"
    const val mpChart = "com.github.PhilJay:MPAndroidChart:$mpChartVersion"
    const val lottie = "com.airbnb.android:lottie:$lottieVersion"
    const val eventBus = "org.greenrobot:eventbus:$eventBusVersion"
    const val liveDataBus = "com.jeremyliao:live-event-bus-x:$liveDataBusVersion"
    const val fastjson = "com.alibaba:fastjson:$fastjsonVersion"
    const val arouter = "com.alibaba:arouter-api:$arouterVersion"
    const val gson = "com.google.code.gson:gson:$gsonVersion"
    const val moshi = "com.squareup.moshi:moshi-kotlin:$moshiVersion"
    const val luban = "top.zibin:Luban:$lubanVersion"
    const val leakCanary = "com.squareup.leakcanary:leakcanary-android:$leakCanaryVersion"
    const val mmkv = "com.tencent:mmkv-static:$mmkvVersion"
    const val protoc = "com.google.protobuf:protoc:$protocVersion"
    const val protobufLite = "com.google.protobuf:protobuf-javalite:$protocVersion"
    const val tbs = "com.tencent.tbs.tbssdk:sdk:$tbsVersion"
    const val baseRvHelper = "com.github.CymChad:BaseRecyclerViewAdapterHelper:$baseRvHelperVersion"
    const val flycoTabLayout = "com.github.ernestchang:flycotablayout:$flycoTabLayoutVersion"
    const val coil = "io.coil-kt:coil:$coilVersion"
    const val utils = "com.blankj:utilcodex:$utilsVersion"

    const val glideKapt = "com.github.bumptech.glide:compiler:$glideVersion"
    const val objectBoxKapt = "io.objectbox:objectbox-processor:$objectBoxVersion"
    const val eventBusKapt = "org.greenrobot:eventbus-annotation-processor:$eventBusVersion"
    const val arouterKapt = "com.alibaba:arouter-compiler:$arouterCompilerVersion"

    val kotlin = arrayOf(
        "org.jetbrains.kotlin:kotlin-stdlib:$kotlinVersion",
        "org.jetbrains.kotlin:kotlin-stdlib-common:$kotlinVersion",
        "org.jetbrains.kotlin:kotlin-stdlib-jdk8:$kotlinVersion",
        "org.jetbrains.kotlin:kotlin-reflect:$kotlinVersion",
        "org.jetbrains.kotlinx:kotlinx-coroutines-core:$coroutinesVersion",
        "org.jetbrains.kotlinx:kotlinx-coroutines-android:$coroutinesVersion"
    )
    val lifecycle = arrayOf(
        "androidx.lifecycle:lifecycle-viewmodel-ktx:$lifecycleVersion",
        "androidx.lifecycle:lifecycle-livedata-ktx:$lifecycleVersion",
        "androidx.lifecycle:lifecycle-runtime-ktx:$lifecycleVersion",
        "androidx.lifecycle:lifecycle-reactivestreams-ktx:$lifecycleVersion",
        "androidx.lifecycle:lifecycle-viewmodel-savedstate:$lifecycleVersion",
        "androidx.lifecycle:lifecycle-service:$lifecycleVersion",
        "androidx.lifecycle:lifecycle-process:$lifecycleVersion",
        "androidx.lifecycle:lifecycle-common-java8:$lifecycleVersion"
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
        "com.scwang.smart:refresh-header-material:$smartRefreshLayoutVersion",
        "com.scwang.smart:refresh-footer-classics:$smartRefreshLayoutVersion"
    )
    val okHttp = arrayOf(
        "com.squareup.okhttp3:okhttp:$okHttpVersion",
        "com.squareup.okhttp3:logging-interceptor:$okHttpVersion"
    )
    val retrofit = arrayOf(
        "com.squareup.retrofit2:retrofit:$retrofitVersion",
        "com.squareup.retrofit2:converter-gson:$retrofitVersion"
    )
    val glide = arrayOf(
        "com.github.bumptech.glide:glide:$glideVersion",
        "com.github.bumptech.glide:okhttp3-integration:$glideVersion",
        "jp.wasabeef:glide-transformations:$glideTransformVersion"
    )
    val objectBox = arrayOf(
        "io.objectbox:objectbox-android:$objectBoxVersion",
        "io.objectbox:objectbox-kotlin:$objectBoxVersion"
    )
    val rx = arrayOf(
        "io.reactivex.rxjava3:rxjava:$rxJavaVersion",
        "io.reactivex.rxjava3:rxandroid:$rxAndroidVersion",
        "com.squareup.retrofit2:adapter-rxjava3:$retrofitVersion",
        "com.github.tbruyelle:rxpermissions:$rxPermissionVersion"
    )
}