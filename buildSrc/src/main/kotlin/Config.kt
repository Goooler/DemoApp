import com.android.build.api.dsl.VariantDimension
import com.android.build.gradle.AbstractAppExtension
import com.android.build.gradle.BaseExtension
import com.android.build.gradle.internal.api.BaseVariantOutputImpl
import org.gradle.api.JavaVersion
import org.gradle.api.Project
import org.gradle.api.artifacts.Dependency
import org.gradle.api.artifacts.dsl.DependencyHandler
import org.gradle.api.plugins.ExtensionAware
import org.gradle.kotlin.dsl.dependencies
import org.gradle.kotlin.dsl.get
import org.gradle.kotlin.dsl.getByName
import org.jetbrains.kotlin.gradle.dsl.KotlinJvmOptions
import org.jetbrains.kotlin.gradle.plugin.KaptExtension
import java.io.File
import java.text.SimpleDateFormat
import java.util.*

// sdk
private const val appTargetSdk = 30
private const val appBuildTool = "30.0.2"
private const val appMinSdk = 23
private val javaVersion = JavaVersion.VERSION_1_8
private val ndkLibs = setOf(
    "armeabi-v7a", "x86"
)

private const val cdnPrefix = "https://raw.githubusercontent.com/"
private val apiHosts = mapOf(
    Flavor.Daily.tag to "https://api.github.com/",
    Flavor.Online.tag to "https://api.github.com/"
)

// app
private const val appVersionName = "1.0"
private const val appVersionCode = 20201109
const val appPackageName = "io.goooler.demoapp"
const val appName = "Demo"

val localLibs = mapOf(
    "dir" to "libs",
    "include" to arrayOf("*.jar", "*.aar")
)

fun DependencyHandler.api(vararg names: Any): Array<Dependency?> =
    names.map {
        add("api", it)
    }.toTypedArray()

fun DependencyHandler.implementation(vararg names: Any): Array<Dependency?> =
    names.map {
        add("implementation", it)
    }.toTypedArray()

fun DependencyHandler.kapt(vararg names: Any): Array<Dependency?> =
    names.map {
        add("kapt", it)
    }.toTypedArray()

fun DependencyHandler.androidTestImplementation(vararg names: Any): Array<Dependency?> =
    names.map {
        add("androidTestImplementation", it)
    }.toTypedArray()

fun getModuleName(module: Module) = ":${module.tag}"

fun getResourcePrefix(module: Module) = "${module.tag}_"

fun getVersionNameSuffix(module: Module) = "_${module.tag}"

fun Project.setupBase(): BaseExtension {
    return extensions.getByName<BaseExtension>("android").apply {
        plugins.run {
            apply(Plugins.kotlinAndroid)
            apply(Plugins.kotlinKapt)
        }
        compileSdkVersion(appTargetSdk)
        buildToolsVersion = appBuildTool
        defaultConfig {
            minSdkVersion(appMinSdk)
            targetSdkVersion(appTargetSdk)
            versionCode = appVersionCode
            versionName = appVersionName
            vectorDrawables.useSupportLibrary = true
            ndk { abiFilters.addAll(ndkLibs) }
        }
        sourceSets["main"].run {
            java.srcDirs("src/main/kotlin")
        }
        compileOptions {
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
            isAbortOnError = false
            isCheckReleaseBuilds = false
        }
    }
}

fun Project.setupCommon(module: Module? = null): BaseExtension {
    return setupBase().apply {
        plugins.apply(Plugins.arouter)
        module?.let {
            resourcePrefix = getResourcePrefix(it)
            defaultConfig.versionNameSuffix = getVersionNameSuffix(it)
        }
        flavorDimensions("channel")
        productFlavors {
            create(Flavor.Daily.tag)
            create(Flavor.Online.tag)
            if (module == Module.Common) {
                all {
                    putBuildConfigIntField(BuildConfigField.VersionCode.tag, appVersionCode)
                    putBuildConfigStringField(BuildConfigField.VersionName.tag, appVersionName)
                    putBuildConfigStringField(BuildConfigField.CdnPrefix.tag, cdnPrefix)
                    putBuildConfigStringField(BuildConfigField.ApiHost.tag, apiHosts[name])
                }
            }
        }
        extensions.getByName<KaptExtension>("kapt").arguments {
            arg("AROUTER_MODULE_NAME", project.name)
        }
        dependencies {
            if (module != Module.Common) {
                implementation(project(getModuleName(Module.Common)))
            }
            kapt(Libs.arouterKapt, Libs.moshiKapt)
        }
    }
}

fun Project.setupApp(appPackageName: String, appName: String): BaseExtension {
    return setupCommon().apply {
        defaultConfig {
            applicationId = appPackageName
            addManifestPlaceholders(
                mapOf(
                    "appName" to appName
                )
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
                signingConfig = signingConfigs["sign"]
                isMinifyEnabled = true
                isZipAlignEnabled = true
                isShrinkResources = true
                proguardFiles(
                    "${rootDir.path}/gradle/proguard-rules.pro"
                )
            }
            getByName("debug") {
                signingConfig = signingConfigs["sign"]
                applicationIdSuffix = ".debug"
                versionNameSuffix = ".debug"
                isJniDebuggable = true
                isRenderscriptDebuggable = true
                isCrunchPngs = false
            }
        }
        buildTypes {
            getByName("release") {
                resValue("string", "app_name", appName)
            }
            getByName("debug") {
                resValue("string", "app_name", "${appName}.debug")
            }
        }
        (this as AbstractAppExtension).applicationVariants.all {
            outputs.all {
                (this as BaseVariantOutputImpl).outputFileName =
                    "../../../../${appName}_${versionName}_${versionCode}_${flavorName}_${buildType.name}.apk"
            }
        }
        compileOptions.isCoreLibraryDesugaringEnabled = true
        dependencies.add("coreLibraryDesugaring", Libs.desugar)
    }
}

private fun Project.findPropertyString(key: String): String = findProperty(key) as String

private fun Project.getSignProperty(key: String): String {
    return Properties().apply {
        rootProject.file("gradle/keystore.properties").inputStream().use {
            load(it)
        }
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