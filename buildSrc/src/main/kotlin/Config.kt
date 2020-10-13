import com.android.build.gradle.BaseExtension
import org.gradle.api.JavaVersion
import org.gradle.api.Project
import org.gradle.api.artifacts.Dependency
import org.gradle.api.artifacts.dsl.DependencyHandler
import org.gradle.api.plugins.ExtensionAware
import org.gradle.kotlin.dsl.getByName
import org.jetbrains.kotlin.gradle.dsl.KotlinJvmOptions
import org.jetbrains.kotlin.gradle.plugin.KaptExtension
import java.text.SimpleDateFormat
import java.util.*

fun DependencyHandler.api(names: Array<String>): Array<Dependency?> =
    names.map {
        add("api", it)
    }.toTypedArray()

fun DependencyHandler.implementation(names: Array<String>): Array<Dependency?> =
    names.map {
        add("implementation", it)
    }.toTypedArray()

/**
 * versionCode 限长 10 位
 */
val buildTime: Int = SimpleDateFormat("yyMMddHHmm", Locale.CHINESE).format(Date()).toInt()

val javaVersion = JavaVersion.VERSION_1_8

val cleanFileTypes = arrayOf("*.log", "*.txt", "*.classpath", "*.project", "*.settings")

val localLibs = mapOf(
    "dir" to "libs",
    "include" to arrayOf("*.jar", "*.aar")
)

val ndkLibs = setOf(
    "armeabi-v7a", "x86"
)

val manifestFields = mapOf(
    "appName" to "Demo"
)

const val cdnPrefix = "https://raw.githubusercontent.com/"

val apiHosts = mapOf(
    Flavor.Daily.tag to "https://api.github.com/",
    Flavor.Online.tag to "https://api.github.com/"
)

fun getModuleName(module: Module) = ":${module.tag}"

fun getResourcePrefix(module: Module) = "${module.tag}_"

fun getVersionNameSuffix(module: Module) = "_${module.tag}"

fun Project.setupCore(): BaseExtension {
    return extensions.getByName<BaseExtension>("android").apply {
        compileSdkVersion(appTargetSdk)
        buildToolsVersion(appBuildTool)
        defaultConfig {
            minSdkVersion(appMinSdk)
            targetSdkVersion(appTargetSdk)
            versionCode = 20201013
            versionName = appVersionName
            vectorDrawables.useSupportLibrary = true
        }
        compileOptions {
            sourceCompatibility = javaVersion
            targetCompatibility = javaVersion
        }
        (this as ExtensionAware).extensions.getByName<KotlinJvmOptions>("kotlinOptions").apply {
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

fun Project.setupCommon(module: Module? = null, useRouter: Boolean = true): BaseExtension {
    return setupCore().apply {
        module?.let {
            resourcePrefix = getResourcePrefix(it)
            defaultConfig.versionNameSuffix = getVersionNameSuffix(it)
        }
        flavorDimensions("channel")
        productFlavors {
            create(Flavor.Daily.tag)
            create(Flavor.Online.tag)
        }
        extensions.getByName<KaptExtension>("kapt").arguments {
            if (useRouter) arg("AROUTER_MODULE_NAME", project.name)
        }
    }
}