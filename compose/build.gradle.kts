setupLib(Module.Compose) {
  buildFeatures {
    compose = true
  }
  composeOptions {
    kotlinCompilerExtensionVersion = composeVersion
  }
  kotlinOptions {
    freeCompilerArgs = freeCompilerArgs + listOf(
      "-Xallow-jvm-ir-dependencies",
      "-P",
      "plugin:androidx.compose.compiler.plugins.kotlin:suppressKotlinVersionCompatibilityCheck=true"
    )
  }
}

dependencies {
  implementations(*Libs.compose, Libs.accompanist)
}
