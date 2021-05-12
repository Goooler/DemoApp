@file:Suppress("UnstableApiUsage")

import com.android.build.api.dsl.VariantDimension
import com.android.build.gradle.BaseExtension
import com.android.build.gradle.LibraryExtension
import com.android.build.gradle.internal.api.ApkVariantOutputImpl
import com.android.build.gradle.internal.dsl.BaseAppModuleExtension
import org.gradle.api.InvalidUserDataException
import org.gradle.api.Project
import org.gradle.api.artifacts.Dependency
import org.gradle.api.artifacts.dsl.DependencyHandler
import org.gradle.api.plugins.ExtensionAware
import org.gradle.api.plugins.PluginAware
import org.gradle.kotlin.dsl.ScriptHandlerScope
import org.gradle.kotlin.dsl.configure
import org.gradle.kotlin.dsl.dependencies
import org.gradle.kotlin.dsl.get
import org.jetbrains.kotlin.gradle.dsl.KotlinJvmOptions
import org.jetbrains.kotlin.gradle.plugin.KaptExtension
import java.io.File
import java.nio.charset.Charset
import java.util.Properties

const val cdnPrefix = "https://raw.githubusercontent.com/"
val apiHosts = mapOf(
  Flavor.Daily.name to "https://api.github.com/",
  Flavor.Online.name to "https://api.github.com/"
)

// app
const val appPackageName = "io.goooler.demoapp"
const val appName = "Demo"
const val extraScriptPath = "gradle/extra.gradle"

val gitCommitCount: String by lazy { "git describe --tags".exec() }
val gitCommitDescribe: Int by lazy { "git rev-list HEAD --count".exec().toInt() }

fun String.exec(): String =
  Runtime.getRuntime().exec(this).inputStream.readBytes().toString(Charset.defaultCharset()).trim()

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

fun PluginAware.applyPlugins(vararg names: String) {
  apply {
    names.forEach(::plugin)
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

fun BaseExtension.kotlinOptions(block: KotlinJvmOptions.() -> Unit) {
  (this as ExtensionAware).extensions.configure("kotlinOptions", block)
}

fun Project.kapt(block: KaptExtension.() -> Unit) = configure(block)

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
      minSdk = 21
      targetSdk = 30
      versionCode = gitCommitDescribe
      versionName = gitCommitCount
      vectorDrawables.useSupportLibrary = true
      ndk.abiFilters += setOf("armeabi-v7a")
      module?.let {
        resourcePrefix = "${it.tag}_"
        versionNameSuffix = "_${it.tag}"
      }
    }
    buildFeatures.buildConfig = false
    kotlinOptions {
      freeCompilerArgs = listOf(
        "-Xjvm-default=all-compatibility"
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
      release {
        resValue("string", "app_name", appName)
        signingConfig = signingConfigs["sign"]
        isMinifyEnabled = true
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
      debug {
        resValue("string", "app_name", "${appName}.debug")
        signingConfig = signingConfigs["sign"]
        applicationIdSuffix = ".debug"
        versionNameSuffix = ".debug"
        isJniDebuggable = true
        isRenderscriptDebuggable = true
        isCrunchPngs = false
      }
    }
    dependenciesInfo.includeInApk = false
    applicationVariants.all {
      outputs.all {
        (this as? ApkVariantOutputImpl)?.outputFileName =
          "../../../../${appName}_${versionName}_${versionCode}_${flavorName}_${buildType.name}.apk"
      }
    }
    block()
  }
}

private inline fun <reified T : BaseExtension> Project.setupCommon(
  module: Module? = null,
  crossinline block: T.() -> Unit = {}
) = setupBase<T>(module) {
  flavorDimensions("channel")
  productFlavors {
    create(Flavor.Daily.name)
    create(Flavor.Online.name)
  }
  kapt {
    arguments {
      arg("AROUTER_MODULE_NAME", project.name)
      arg("room.incremental", "true")
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
    kapts(Libs.arouterApt, Libs.moshiApt, Libs.roomApt, *Libs.hiltApt)
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
