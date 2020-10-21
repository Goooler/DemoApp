import com.google.protobuf.gradle.builtins
import com.google.protobuf.gradle.generateProtoTasks
import com.google.protobuf.gradle.protobuf
import com.google.protobuf.gradle.protoc

plugins {
    id(Plugins.androidLibrary)
    id(Plugins.kotlinAndroid)
    id(Plugins.kotlinKapt)
    id(Plugins.protobuf)
}

setupCommon(Module.Main).run {
    protobuf {
        protoc {
            artifact = Libs.protoc
        }
        generateProtoTasks {
            all().forEach {
                it.builtins {
                    create("java") {
                        option("lite")
                    }
                }
            }
        }
    }
}

dependencies {
    implementation(project(getModuleName(Module.Common)))
    implementation(project(getModuleName(Module.Adapter)))

    implementation(Libs.protobufLite)
    implementation(Libs.photoView)
}