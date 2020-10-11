import com.android.build.gradle.BaseExtension
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

object ApiHosts {
    const val daily = "https://api.github.com/"
    const val online = "https://api.github.com/"
}

fun Project.setupCommon(): BaseExtension {
    return extensions.getByName<BaseExtension>("android").apply {
        compileSdkVersion(appTargetSdk)
        buildToolsVersion(appBuildTool)
        defaultConfig {
            minSdkVersion(appMinSdk)
            targetSdkVersion(appTargetSdk)
            versionCode = buildTime
            versionName = appVersionName
            vectorDrawables.useSupportLibrary = true
        }
        dataBinding.isEnabled = true
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

fun BaseExtension.setupFlavors() {
    flavorDimensions("channel")
    productFlavors {
        create("daily")
        create("online")
    }
}

fun KaptExtension.kaptCommon() {
    arguments {
        arg("AROUTER_MODULE_NAME", project.name)
    }
}