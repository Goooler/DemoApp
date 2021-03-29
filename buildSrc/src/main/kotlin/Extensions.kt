@file:Suppress("UnstableApiUsage")

import com.android.build.api.dsl.VariantDimension
import com.android.build.gradle.BaseExtension
import com.android.build.gradle.LibraryExtension
import com.android.build.gradle.internal.api.ApkVariantOutputImpl
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
import org.jetbrains.kotlin.gradle.dsl.KotlinJvmOptions
import org.jetbrains.kotlin.gradle.plugin.KaptExtension
import java.io.File
import java.util.Locale
import java.util.Properties

const val cdnPrefix = "https://raw.githubusercontent.com/"
val apiHosts = mapOf(
  Flavor.Daily.flavor to "https://api.github.com/",
  Flavor.Online.flavor to "https://api.github.com/"
)

// app
const val appPackageName = "io.goooler.demoapp"
const val appName = "Demo"
const val extraScriptPath = "gradle/extra.gradle.kts"

val gitCommitCount: String by lazy { "git describe --tags".exec() }
val gitCommitDescribe: Int by lazy { "git rev-list HEAD --count".exec().toInt() }

fun String.exec(): String = String(Runtime.getRuntime().exec(this).inputStream.readBytes()).trim()

fun ScriptHandlerScope.classpaths(vararg names: Any): Array<Dependency?> =
  dependencies.config("classpath", *names)

fun DependencyHandler.apis(vararg names: Any): Array<Dependency?> = config("api", *names)

fun DependencyHandler.implementations(vararg names: Any): Array<Dependency?> =
  config("implementation", *names)

fun DependencyHandler.debugImplementations(vararg names: Any): Array<Dependency?> =
  config("debugImplementation", *names)

fun DependencyHandler.releaseImplementations(vararg names: Any): Array<Dependency?> =
  config("releaseImplementation", *names)

fun DependencyHandler.kapts(vararg names: Any): Array<Dependency?> = config("kapt", *names)

fun DependencyHandler.androidTestImplementations(vararg names: Any): Array<Dependency?> =
  config("androidTestImplementation", *names)

fun DependencyHandler.testImplementations(vararg names: Any): Array<Dependency?> =
  config("testImplementation", *names)

inline val Module.moduleName: String get() = ":${tag}"

fun String.isStableVersion(): Boolean {
  val stableKeyword = listOf("RELEASE", "FINAL", "GA").any { toUpperCase(Locale.ROOT).contains(it) }
  return stableKeyword || Regex("^[0-9,.v-]+(-r)?$").matches(this)
}

fun PluginAware.applyPlugins(vararg names: String) {
  apply {
    names.forEach { plugin(it) }
  }
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

inline fun BaseExtension.kotlinOptions(crossinline block: KotlinJvmOptions.() -> Unit) {
  (this as ExtensionAware).extensions.configure<KotlinJvmOptions>("kotlinOptions") { block() }
}

inline fun <reified T : BaseExtension> Project.setupBase(
  module: Module? = null,
  crossinline block: T.() -> Unit = {}
) {
  when (T::class) {
    LibraryExtension::class -> Plugins.androidLibrary
    BaseAppModuleExtension::class -> Plugins.androidApplication
    else -> null
  }?.let { applyPlugins(it) }

  applyPlugins(Plugins.kotlinAndroid, Plugins.kotlinKapt)
  extensions.configure<BaseExtension>("android") {
    compileSdkVersion(30)
    buildToolsVersion("30.0.3")
    defaultConfig {
      minSdkVersion(21)
      targetSdkVersion(30)
      versionCode = gitCommitDescribe
      versionName = gitCommitCount
      vectorDrawables.useSupportLibrary = true
      ndk.abiFilters += setOf("arm64-v8a")
      module?.let {
        resourcePrefix = "${it.tag}_"
        versionNameSuffix = "_${it.tag}"
      }
    }
    buildFeatures.buildConfig = false
    compileOptions.incremental = true
    kotlinOptions {
      jvmTarget = JavaVersion.VERSION_1_8.toString()
      useIR = true
      freeCompilerArgs = listOf(
        "-Xjvm-default=compatibility"
      )
    }
    lintOptions {
      isAbortOnError = true
      isCheckReleaseBuilds = true
    }
    dependencies {
      // local
      implementations(fileTree(mapOf("dir" to "libs", "include" to arrayOf("*.jar", "*.aar"))))
    }
    (this as T).block()
  }
}

fun Project.setupLib(
  module: Module? = null,
  block: LibraryExtension.() -> Unit = {}
) {
  setupCommon(module, block)
}

fun Project.setupApp(
  appPackageName: String,
  appName: String,
  block: BaseAppModuleExtension.() -> Unit = {}
) {
  setupCommon<BaseAppModuleExtension> {
    defaultConfig {
      applicationId = appPackageName
      manifestPlaceholders += mapOf("appName" to appName)
      resourceConfigurations += setOf("en", "zh-rCN", "xxhdpi")
    }
    signingConfigs.create("sign") {
      keyAlias = getSignProperty("keyAlias")
      keyPassword = getSignProperty("keyPassword")
      storeFile = File(rootDir.path, getSignProperty("storeFile"))
      storePassword = getSignProperty("storePassword")
    }
    buildTypes {
      named("release") {
        resValue("string", "app_name", appName)
        signingConfig = signingConfigs["sign"]
        isMinifyEnabled = true
        isShrinkResources = true
        proguardFiles("${rootDir.path}/gradle/proguard-rules.pro")
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
          "com/**",
          "kotlin/**",
          "kotlinx/**",
          "okhttp3/**",
          "google/**"
        )
      }
      named("debug") {
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
        (this as? ApkVariantOutputImpl)?.outputFileName =
          "../../../../${appName}_${versionName}_${versionCode}_${flavorName}_${buildType.name}.apk"
      }
    }
    compileOptions.isCoreLibraryDesugaringEnabled = true
    dependencies.add("coreLibraryDesugaring", Libs.desugar)
    block()
  }
}

private inline fun <reified T : BaseExtension> Project.setupCommon(
  module: Module? = null,
  crossinline block: T.() -> Unit = {}
) = setupBase<T>(module) {
  flavorDimensions("channel")
  productFlavors {
    create(Flavor.Daily.flavor)
    create(Flavor.Online.flavor)
  }
  extensions.configure<KaptExtension>("kapt") {
    arguments {
      arg("AROUTER_MODULE_NAME", project.name)
      arg("room.schemaLocation", "$projectDir/build")
      arg("room.incremental", "true")
      arg("room.expandProjection", "true")
    }
  }
  dependencies {
    if (module != Module.Common) {
      implementations(project(Module.Common.moduleName))
    }
    implementations(
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
  block()
}

private fun DependencyHandler.config(operation: String, vararg names: Any): Array<Dependency?> =
  names.map { add(operation, it) }.toTypedArray()

private fun Project.findPropertyString(key: String): String = property(key).toString()

private fun Project.getSignProperty(
  key: String,
  path: String = "gradle/keystore.properties"
): String = Properties().apply {
  rootProject.file(path).inputStream().use(::load)
}.getProperty(key)
