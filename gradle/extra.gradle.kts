val kotlinVersion = "1.6.10"

rootProject.extra.run {
  set("androidPlugin", "com.android.tools.build:gradle:7.0.4")
  set("kotlinPlugin", "org.jetbrains.kotlin:kotlin-gradle-plugin:$kotlinVersion")
}

repositories {
  google()
  mavenCentral()
  maven("https://maven.aliyun.com/repository/public")
  maven("https://jitpack.io")
}

configurations.all {
  resolutionStrategy.eachDependency {
    when {
      requested.name.startsWith("kotlin-stdlib") -> {
        useTarget("${requested.group}:${requested.name.replace("jre", "jdk")}:${kotlinVersion}")
      }
      requested.name.startsWith("kotlinx-coroutines") -> {
        useTarget("${requested.group}:${requested.name}:1.6.0")
      }
      else -> when (requested.group) {
        "org.jetbrains.kotlin" -> useVersion(kotlinVersion)
        "com.android.support" -> {
          if ("multidex" !in requested.name) useVersion("28.0.0")
        }
      }
    }
  }
}

tasks.withType<JavaCompile>().configureEach {
  options.encoding = Charsets.UTF_8.toString()
}
