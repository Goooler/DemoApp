import com.android.build.gradle.BaseExtension
import dagger.hilt.android.plugin.HiltExtension
import kotlin.math.pow
import org.gradle.kotlin.dsl.get
import org.jetbrains.kotlin.gradle.dsl.KotlinJvmOptions
import org.jlleitschuh.gradle.ktlint.KtlintExtension
import com.android.build.gradle.internal.dsl.BaseAppModuleExtension
import java.util.Properties

plugins {
  id(libs.plugins.android.application.get().pluginId) apply false
  id(libs.plugins.android.library.get().pluginId) apply false
  id(libs.plugins.kotlin.android.get().pluginId) apply false
  alias(libs.plugins.hilt) apply false
  alias(libs.plugins.ktlint) apply false
  alias(libs.plugins.moshiX) apply false
}

allprojects {
  apply(plugin = rootProject.libs.plugins.ktlint.get().pluginId)
  configure<KtlintExtension> {
    version.set(rootProject.libs.versions.ktlint.get())
  }

  plugins.withId(rootProject.libs.plugins.hilt.get().pluginId) {
    configure<HiltExtension> {
      enableAggregatingTask = true
    }
  }

  configurations.all {
    resolutionStrategy.eachDependency {
      when (requested.group) {
        "com.android.support" -> {
          if ("multidex" !in requested.name) useVersion(libs.versions.support.get())
        }
        libs.androidX.appCompat.get().module.group -> useVersion(libs.versions.androidX.appCompat.get())
        libs.androidX.activity.compose.get().module.group -> useVersion(libs.versions.androidX.activity.get())
        libs.androidX.collection.get().module.group -> useVersion(libs.versions.androidX.collection.get())
        libs.androidX.core.get().module.group -> useVersion(libs.versions.androidX.core.get())
        libs.androidX.fragment.get().module.group -> useVersion(libs.versions.androidX.fragment.get())
        libs.gradlePlugin.kotlin.get().module.group -> useVersion(libs.versions.kotlin.get())
        libs.square.okHttp.logInterceptor.get().module.group -> useVersion(libs.versions.square.okHttp.get())
        else -> when {
          requested.name.startsWith("kotlinx-coroutines") ->
            useVersion(libs.versions.coroutines.get())
        }
      }
    }
  }

  tasks.withType<Test> {
    useJUnitPlatform()
  }
}

tasks {
  create<Delete>("clean") {
    val customFileTypes = fileTree(
      mapOf(
        "dir" to "$rootDir/gradle",
        "include" to arrayOf("*.log", "*.txt")
      )
    )
    delete(rootProject.buildDir, customFileTypes)
  }
  wrapper {
    distributionType = Wrapper.DistributionType.ALL
  }
}

subprojects {
  plugins.withId(rootProject.libs.plugins.android.library.get().pluginId) {
    if (name.startsWith("biz-") || name.startsWith("common")) setupCommon() else setupBase()
  }
  plugins.withId(rootProject.libs.plugins.android.application.get().pluginId) {
    setupApp()
  }
}

val appVersionName = "1.5.0"
val appName = "DemoApp"
val appVersionCode = appVersionName.versionCode
val javaVersion = JavaVersion.VERSION_11

val String.versionCode: Int
  get() = takeWhile { it.isDigit() || it == '.' }
    .split('.')
    .map { it.toInt() }
    .reversed()
    .sumByIndexed { index, unit ->
      // 1.2.3 -> 102030
      (unit * 10.0.pow(2 * index + 1)).toInt()
    }



val Project.shortName: String get() = name.removePrefix("biz-")

fun Project.setupBase(): BaseExtension {
  return extensions.getByName<BaseExtension>("android").apply {
    resourcePrefix = "${shortName}_"
    namespace = "io.goooler.demoapp.$shortName"
    compileSdkVersion(32)
    defaultConfig {
      minSdk = 21
      vectorDrawables.useSupportLibrary = true
      testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }
    sourceSets.configureEach {
      java.srcDirs("src/$name/kotlin")
    }
    buildFeatures.buildConfig = false
    compileOptions.setDefaultJavaVersion(JavaVersion.VERSION_11)
    /*(this as ExtensionAware).extensions.configure<KotlinJvmOptions> {
      jvmTarget = JavaVersion.VERSION_11.toString()
      // https://youtrack.jetbrains.com/issue/KT-41985
      freeCompilerArgs += listOf(
        "-progressive",
        "-opt-in=kotlin.RequiresOptIn",
        "-Xjvm-default=all"
      )
    }*/
    packagingOptions.resources.excludes += setOf(
      "**/*.proto",
      "**/*.bin",
      "**/*.java",
      "**/*.properties",
      "**/*.version",
      "**/*.*_module",
      "*.txt",
      "META-INF/services/**",
      "META-INF/com/**",
      "META-INF/licenses/**",
      "META-INF/AL2.0",
      "META-INF/LGPL2.1",
      "com/**",
      "kotlin/**",
      "kotlinx/**",
      "okhttp3/**",
      "google/**"
    )
    (this as? com.android.build.api.dsl.CommonExtension<*, *, *, *>)?.lint {
      abortOnError = true
    }
  }
}

fun Project.setupCommon(): BaseExtension = setupBase().apply {
  flavorDimensions("channel")
  productFlavors {
    create("dev")
    create("prod")
  }
}

fun Project.setupApp(): BaseAppModuleExtension = (setupCommon() as BaseAppModuleExtension).apply {
  defaultConfig {
    applicationId = namespace
    targetSdk = 32
    versionCode = appVersionCode
    versionName = appVersionName
    resourceConfigurations += setOf("en", "zh-rCN", "xxhdpi")
  }
  signingConfigs.create("release") {
    keyAlias = getSignProperty("keyAlias")
    keyPassword = getSignProperty("keyPassword")
    storeFile = File(rootDir, getSignProperty("storeFile"))
    storePassword = getSignProperty("storePassword")
    enableV3Signing = true
    enableV4Signing = true
  }
  buildTypes {
    release {
      resValue("string", "app_name", appName)
      signingConfig = signingConfigs["release"]
      isMinifyEnabled = true
      isShrinkResources = true
      proguardFiles("$rootDir/gradle/proguard-rules.pro")
    }
    debug {
      resValue("string", "app_name", "${appName}.debug")
      signingConfig = signingConfigs["release"]
      isJniDebuggable = true
      isRenderscriptDebuggable = true
      isCrunchPngs = false
    }
  }
  dependenciesInfo.includeInApk = false
  applicationVariants.all {
    outputs.all {
      (this as? com.android.build.gradle.internal.api.ApkVariantOutputImpl)?.outputFileName =
        "../../../../" +
          "${appName}_${versionName}_${versionCode}_${flavorName}_${buildType.name}.apk"
    }
  }
}

fun Project.getSignProperty(
  key: String, path: String = "gradle/keystore.properties"
): String = Properties().apply {
  rootProject.file(path).inputStream().use(::load)
}.getProperty(key)

fun <T> List<T>.sumByIndexed(selector: (Int, T) -> Int): Int {
  var index = 0
  var sum = 0
  for (element in this) {
    sum += selector(index++, element)
  }
  return sum
}
