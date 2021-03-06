@file:Suppress("UnstableApiUsage")

import com.android.build.api.dsl.VariantDimension
import com.android.build.gradle.BaseExtension
import com.android.build.gradle.LibraryExtension
import com.android.build.gradle.internal.api.BaseVariantOutputImpl
import com.android.build.gradle.internal.dsl.BaseAppModuleExtension
import org.gradle.api.InvalidUserDataException
import org.gradle.api.JavaVersion
import org.gradle.api.Project
import org.gradle.api.artifacts.Dependency
import org.gradle.api.artifacts.dsl.DependencyHandler
import org.gradle.api.plugins.ExtensionAware
import org.gradle.api.plugins.PluginAware
import org.gradle.kotlin.dsl.ScriptHandlerScope
import org.gradle.kotlin.dsl.dependencies
import org.gradle.kotlin.dsl.get
import org.gradle.kotlin.dsl.getByName
import org.jetbrains.kotlin.gradle.dsl.KotlinJvmOptions
import org.jetbrains.kotlin.gradle.plugin.KaptExtension
import java.io.File
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import java.util.Properties

// sdk
private const val globalTargetSdk = 30
private const val globalBuildTool = "30.0.3"
private const val globalMinSdk = 23
private val javaVersion = JavaVersion.VERSION_1_8
private val ndkLibs = setOf("arm64-v8a")

const val cdnPrefix = "https://raw.githubusercontent.com/"
val apiHosts = mapOf(
  Flavor.Daily.flavor to "https://api.github.com/",
  Flavor.Online.flavor to "https://api.github.com/"
)

// app
const val globalVersionName = "1.0"
const val globalVersionCode = 20210107
const val appPackageName = "io.goooler.demoapp"
const val appName = "Demo"
const val extraScriptPath = "buildSrc/extra.gradle.kts"

val localLibs = mapOf(
  "dir" to "libs",
  "include" to arrayOf("*.jar", "*.aar")
)

fun ScriptHandlerScope.classpaths(vararg names: Any) {
  dependencies {
    for (name in names) {
      add("classpath", name)
    }
  }
}

fun DependencyHandler.apis(vararg names: Any): Array<Dependency?> =
  names.map {
    add("api", it)
  }.toTypedArray()

fun DependencyHandler.implementations(vararg names: Any): Array<Dependency?> =
  names.map {
    add("implementation", it)
  }.toTypedArray()

fun DependencyHandler.debugImplementations(vararg names: Any): Array<Dependency?> =
  names.map {
    add("debugImplementation", it)
  }.toTypedArray()

fun DependencyHandler.releaseImplementations(vararg names: Any): Array<Dependency?> =
  names.map {
    add("releaseImplementation", it)
  }.toTypedArray()

fun DependencyHandler.kapts(vararg names: Any): Array<Dependency?> =
  names.map {
    add("kapt", it)
  }.toTypedArray()

fun DependencyHandler.androidTestImplementations(vararg names: Any): Array<Dependency?> =
  names.map {
    add("androidTestImplementation", it)
  }.toTypedArray()

fun DependencyHandler.testImplementations(vararg names: Any): Array<Dependency?> =
  names.map {
    add("testImplementation", it)
  }.toTypedArray()

val Module.moduleName: String get() = ":${tag}"

fun String.isStableVersion(): Boolean {
  val stableKeyword = listOf("RELEASE", "FINAL", "GA").any { toUpperCase(Locale.ROOT).contains(it) }
  return stableKeyword || Regex("^[0-9,.v-]+(-r)?$").matches(this)
}

fun PluginAware.applyPlugins(vararg names: String, block: () -> Unit = {}) {
  apply {
    for (name in names) {
      plugin(name)
    }
  }
  block()
}

fun ExtensionAware.getExtra(name: String): Any {
  return extensions.extraProperties[name]
    ?: throw InvalidUserDataException("ExtraProperty $name is null")
}

fun VariantDimension.putBuildConfigStringField(name: String, value: String?) {
  buildConfigField("String", name, "\"$value\"")
}

fun VariantDimension.putBuildConfigIntField(name: String, value: Int) {
  buildConfigField("Integer", name, value.toString())
}

fun BaseExtension.kotlinOptions(block: KotlinJvmOptions.() -> Unit) {
  (this as ExtensionAware).extensions.getByName<KotlinJvmOptions>("kotlinOptions").block()
}

fun Project.setupBase(module: Module? = null, block: BaseExtension.() -> Unit = {}): BaseExtension {
  applyPlugins(Plugins.kotlinAndroid, Plugins.kotlinKapt)
  return extensions.getByName<BaseExtension>("android").apply {
    compileSdkVersion(globalTargetSdk)
    buildToolsVersion = globalBuildTool
    defaultConfig {
      minSdkVersion(globalMinSdk)
      targetSdkVersion(globalTargetSdk)
      versionCode = globalVersionCode
      versionName = globalVersionName
      vectorDrawables.useSupportLibrary = true
      ndk { abiFilters.addAll(ndkLibs) }
      module?.let {
        resourcePrefix = "${it.tag}_"
        versionNameSuffix = "_${it.tag}"
      }
    }
    sourceSets["main"].java.srcDirs("src/main/kotlin")
    compileOptions {
      incremental = true
      setDefaultJavaVersion(javaVersion)
      sourceCompatibility = javaVersion
      targetCompatibility = javaVersion
    }
    kotlinOptions {
      jvmTarget = javaVersion.toString()
      useIR = true
      freeCompilerArgs = listOf(
        "-Xjvm-default=compatibility"
      )
    }
    lintOptions {
      isAbortOnError = true
      isCheckReleaseBuilds = true
    }
    block()
  }
}

fun Project.setupModule(
  module: Module? = null,
  block: LibraryExtension.() -> Unit = {}
): LibraryExtension {
  applyPlugins(Plugins.androidLibrary)
  return (setupCommon(module) as LibraryExtension).apply {
    block()
  }
}

fun Project.setupApp(
  appPackageName: String,
  appName: String,
  block: BaseAppModuleExtension.() -> Unit = {}
): BaseAppModuleExtension {
  applyPlugins(Plugins.androidApplication)
  return (setupCommon() as BaseAppModuleExtension).apply {
    defaultConfig {
      applicationId = appPackageName
      addManifestPlaceholders(
        mapOf("appName" to appName)
      )
    }
    signingConfigs {
      create("sign") {
        keyAlias = getSignProperty("keyAlias")
        keyPassword = getSignProperty("keyPassword")
        storeFile = File(rootDir.path, getSignProperty("storeFile"))
        storePassword = getSignProperty("storePassword")
      }
    }
    buildTypes {
      getByName("release") {
        resValue("string", "app_name", appName)
        signingConfig = signingConfigs["sign"]
        isMinifyEnabled = true
        isShrinkResources = true
        proguardFiles("${rootDir.path}/gradle/proguard-rules.pro")
        packagingOptions.resources.excludes += setOf(
          "**/*.proto",
          "**/*.bin",
          "**/*.java",
          "**/*.version",
          "**/*.*_module",
          "*.txt",
          "META-INF/services/**",
          "META-INF/com/**",
          "com/**",
          "kotlin/**",
          "kotlinx/**",
          "okhttp3/**",
          "google/**"
        )
      }
      getByName("debug") {
        resValue("string", "app_name", "${appName}.debug")
        signingConfig = signingConfigs["sign"]
        applicationIdSuffix = ".debug"
        versionNameSuffix = ".debug"
        isJniDebuggable = true
        isRenderscriptDebuggable = true
        isCrunchPngs = false
      }
    }
    applicationVariants.all {
      outputs.all {
        (this as BaseVariantOutputImpl).outputFileName =
          "../../../../${appName}_${versionName}_${versionCode}_${flavorName}_${buildType.name}.apk"
      }
    }
    compileOptions.isCoreLibraryDesugaringEnabled = true
    dependencies.add("coreLibraryDesugaring", Libs.desugar)
    block()
  }
}

private fun Project.setupCommon(module: Module? = null): BaseExtension {
  return setupBase(module) {
    flavorDimensions("channel")
    productFlavors {
      create(Flavor.Daily.flavor)
      create(Flavor.Online.flavor)
    }
    extensions.getByName<KaptExtension>("kapt").arguments {
      arg("AROUTER_MODULE_NAME", project.name)
      arg("room.schemaLocation", "$projectDir/build")
      arg("room.incremental", "true")
      arg("room.expandProjection", "true")
    }
    dependencies {
      if (module != Module.Common) {
        implementations(project(Module.Common.moduleName))
      }
      implementations(
        // local
        fileTree(localLibs),
        project(Module.Base.moduleName),

        // router
        Libs.arouter,

        // UI
        Libs.constraintLayout,
        Libs.cardView,
        Libs.material,
        *Libs.smartRefreshLayout,
        Libs.photoView,

        // utils
        *Libs.hilt,
        *Libs.room,
        *Libs.rx,
        *Libs.moshi,
        Libs.collection,
        Libs.utils,
        Libs.permissionX
      )
      kapts(Libs.arouterKapt, Libs.moshiKapt, Libs.roomKapt, *Libs.hiltKapt)
    }
    applyPlugins(Plugins.kotlinParcelize, Plugins.arouter, Plugins.hilt)
  }
}

private fun Project.findPropertyString(key: String): String = property(key).toString()

private fun Project.getSignProperty(
  key: String,
  path: String = "gradle/keystore.properties"
): String {
  return Properties().apply {
    rootProject.file(path).inputStream().use(::load)
  }.getProperty(key)
}

/**
 * versionCode 限长 10 位
 */
private val buildTime: Int = SimpleDateFormat("yyMMddHHmm", Locale.CHINESE).format(Date()).toInt()
