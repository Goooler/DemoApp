package io.goooler.android.transform

import com.android.build.gradle.BaseExtension
import org.gradle.api.Plugin
import org.gradle.api.Project

class CustomTransPlugin : Plugin<Project> {

  override fun apply(target: Project) {
    target.extensions.findByType(BaseExtension::class.java)?.registerTransform(CustomTransform)
  }
}