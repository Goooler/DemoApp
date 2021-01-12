import com.android.build.api.dsl.VariantDimension
import com.android.build.gradle.BaseExtension
import com.android.build.gradle.LibraryExtension
import com.android.build.gradle.internal.api.BaseVariantOutputImpl
import com.android.build.gradle.internal.dsl.BaseAppModuleExtension
import org.gradle.api.GradleException
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

private const val cdnPrefix = "https://raw.githubusercontent.com/"
private val apiHosts = mapOf(
  Flavor.Daily.flavor to "https://api.github.com/",
  Flavor.Online.flavor to "https://api.github.com/"
)

// app
private const val globalVersionName = "1.0"
private const val globalVersionCode = 20210107
const val appPackageName = "io.goooler.demoapp"
const val appName = "Demo"
const val extraScriptPath = "buildSrc/extra.gradle.kts"

val localLibs = mapOf(
  "dir" to "libs",
  "include" to arrayOf("*.jar", "*.aar")
)

fun ScriptHandlerScope.classpath(vararg names: Any) {
  dependencies {
    for (name in names) {
      add("classpath", name)
    }
  }
}

fun DependencyHandler.api(vararg names: Any): Array<Dependency?> =
  names.map {
    add("api", it)
  }.toTypedArray()

fun DependencyHandler.implementation(vararg names: Any): Array<Dependency?> =
  names.map {
    add("implementation", it)
  }.toTypedArray()

fun DependencyHandler.debugImplementation(vararg names: Any): Array<Dependency?> =
  names.map {
    add("debugImplementation", it)
  }.toTypedArray()

fun DependencyHandler.kapt(vararg names: Any): Array<Dependency?> =
  names.map {
    add("kapt", it)
  }.toTypedArray()

fun DependencyHandler.androidTestImplementation(vararg names: Any): Array<Dependency?> =
  names.map {
    add("androidTestImplementation", it)
  }.toTypedArray()

fun DependencyHandler.testImplementation(vararg names: Any): Array<Dependency?> =
  names.map {
    add("testImplementation", it)
  }.toTypedArray()

val Module.moduleName: String get() = ":${tag}"

fun String.isStableVersion(): Boolean {
  val stableKeyword = listOf("RELEASE", "FINAL", "GA").any { toUpperCase(Locale.ROOT).contains(it) }
  return stableKeyword || "^[0-9,.v-]+(-r)?$".toRegex().matches(this)
}

fun PluginAware.applyPlugin(vararg names: String) {
  apply {
    for (name in names) {
      plugin(name)
    }
  }
}

fun ExtensionAware.getExtra(name: String): Any {
  return extensions.extraProperties.get(name)
    ?: throw GradleException("ExtraProperty $name is null")
}

fun Project.setupBase(module: Module? = null, block: BaseExtension.() -> Unit = {}): BaseExtension {
  applyPlugin(Plugins.kotlinAndroid, Plugins.kotlinKapt)
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
    (this as ExtensionAware).extensions.getByName<KotlinJvmOptions>("kotlinOptions").run {
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
  applyPlugin(Plugins.androidLibrary)
  return (setupCommon(module) as LibraryExtension).apply {
    block()
  }
}

fun Project.setupApp(
  appPackageName: String,
  appName: String,
  block: BaseAppModuleExtension.() -> Unit = {}
): BaseAppModuleExtension {
  applyPlugin(Plugins.androidApplication)
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
        packagingOptions {
          exclude("**/*.proto")
          exclude("**/*.bin")
          exclude("**/*.java")
          exclude("**/*.version")
          exclude("**/*.*_module")
          exclude("META-INF/services/**")
          exclude("META-INF/com/**")
          exclude("kotlin/**")
          exclude("okhttp3/**")
          exclude("google/**")
        }
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
    dependencies.run {
      add("coreLibraryDesugaring", Libs.desugar)
    }
    block()
  }
}

private fun Project.setupCommon(module: Module? = null): BaseExtension {
  return setupBase(module) {
    flavorDimensions("channel")
    productFlavors {
      create(Flavor.Daily.flavor)
      create(Flavor.Online.flavor)
      if (module == Module.Common) {
        all {
          putBuildConfigIntField(BuildConfigField.VersionCode.tag, globalVersionCode)
          putBuildConfigStringField(BuildConfigField.VersionName.tag, globalVersionName)
          putBuildConfigStringField(BuildConfigField.CdnPrefix.tag, cdnPrefix)
          putBuildConfigStringField(BuildConfigField.ApiHost.tag, apiHosts[name])
        }
      }
    }
    extensions.getByName<KaptExtension>("kapt").arguments {
      arg("AROUTER_MODULE_NAME", project.name)
      arg("room.schemaLocation", "$projectDir/build")
      arg("room.incremental", "true")
      arg("room.expandProjection", "true")
    }
    dependencies {
      if (module != Module.Common) {
        implementation(project(Module.Common.moduleName))
      }
      implementation(
        // local
        fileTree(localLibs),
        project(Module.Base.moduleName),

        // router
        Libs.arouter,

        // UI
        *Libs.smartRefreshLayout,
        Libs.photoView,

        // utils
        *Libs.hilt,
        *Libs.room,
        *Libs.dataStore,
        *Libs.rx,
        *Libs.moshi,
        Libs.collection,
        Libs.utils,
        Libs.permissionX
      )
      kapt(Libs.arouterKapt, Libs.moshiKapt, Libs.roomKapt, *Libs.hiltKapt)
    }
    applyPlugin(Plugins.arouter, Plugins.hilt)
  }
}

private fun Project.findPropertyString(key: String): String = findProperty(key) as String

private fun Project.getSignProperty(
  key: String,
  path: String = "gradle/keystore.properties"
): String {
  return Properties().apply {
    rootProject.file(path).inputStream().use(::load)
  }.getProperty(key)
}

private fun VariantDimension.putBuildConfigStringField(name: String, value: String?) {
  buildConfigField("String", name, "\"$value\"")
}

private fun VariantDimension.putBuildConfigIntField(name: String, value: Int) {
  buildConfigField("Integer", name, value.toString())
}

/**
 * versionCode 限长 10 位
 */
private val buildTime: Int = SimpleDateFormat("yyMMddHHmm", Locale.CHINESE).format(Date()).toInt()