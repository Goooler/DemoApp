import org.gradle.api.artifacts.Dependency
import org.gradle.api.artifacts.dsl.DependencyHandler
import java.text.SimpleDateFormat
import java.util.*

fun DependencyHandler.`api`(names: Array<String>): Array<Dependency?> =
    names.map {
        add("api", it)
    }.toTypedArray()

/**
 * versionCode 限长 10 位
 */
val buildTime: Int = SimpleDateFormat("yyMMddHHmm", Locale.CHINESE).format(Date()).toInt()

val cleanFileTypes = arrayOf(
    "*.log", "*.txt"
)
const val ndkLibs = "armeabi-v7a"

val localLibs = mapOf(
    "dir" to "libs",
    "include" to arrayOf("*.jar", "*.aar")
)

object ApiHosts {
    const val daily = "https://api.github.com/"
    const val online = "https://api.github.com/"
}