import com.google.protobuf.gradle.generateProtoTasks
import com.google.protobuf.gradle.protobuf
import com.google.protobuf.gradle.protoc

plugins {
  id(Plugins.androidLibrary)
  id(Plugins.protobuf)
}

setupCommon(Module.Main).run {
  protobuf {
    protoc {
      artifact = Libs.protoc
    }
    generateProtoTasks {
      all().forEach {
        it.builtins.create("java") {
          option("lite")
        }
      }
    }
  }
}

dependencies {
  implementation(
    project(Module.Adapter.moduleName),
    project(Module.Widget.moduleName),
    Libs.protobufLite
  )
}
