package io.goooler.android.transform

import com.android.build.api.transform.Format
import com.android.build.api.transform.QualifiedContent
import com.android.build.api.transform.Transform
import com.android.build.api.transform.TransformInvocation
import com.android.build.gradle.internal.pipeline.TransformManager

object CustomTransform : Transform() {

  override fun getName(): String = "custom"

  override fun getInputTypes(): MutableSet<QualifiedContent.ContentType> =
    TransformManager.CONTENT_CLASS

  override fun getScopes(): MutableSet<in QualifiedContent.Scope> =
    TransformManager.SCOPE_FULL_PROJECT

  override fun isIncremental(): Boolean = true

  override fun transform(transformInvocation: TransformInvocation) {
    val output = transformInvocation.outputProvider
    transformInvocation.inputs.forEach { input ->
      input.jarInputs.forEach {
        val dest = output.getContentLocation(it.name, it.contentTypes, it.scopes, Format.JAR)
        it.file.copyTo(dest)
      }
      input.directoryInputs.forEach {
        val dest = output.getContentLocation(it.name, it.contentTypes, it.scopes, Format.DIRECTORY)
        it.file.copyTo(dest)
      }
    }
  }
}