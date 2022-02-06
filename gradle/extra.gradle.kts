val kotlinVersion = "1.6.10"

rootProject.extra.run {
  set("androidPlugin", "com.android.tools.build:gradle:7.1.1")
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
      requested.group == "org.jetbrains.kotlin" -> useVersion(kotlinVersion)
      requested.name == "javapoet" -> useVersion("1.13.0")
    }
  }
}

tasks.withType<JavaCompile>().configureEach {
  options.encoding = Charsets.UTF_8.toString()
}
