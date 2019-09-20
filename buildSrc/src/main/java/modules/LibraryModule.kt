package modules

import modules.LibraryType.Android
import modules.LibraryType.DynamicFeature
import modules.LibraryType.Kotlin
import java.io.File

class LibraryModule(
    private val rootDir: File,
    private val type: LibraryType
) {

    fun script() = "$rootDir/buildSrc/shared/${target()}"

    private fun target() = when (type) {
        Kotlin -> kotlinBuildLogic
        Android -> androidBuildLogic
        DynamicFeature -> androidDynamicFeatureLogic
    }

    // Why using .gradle instead of .gradle.kts files:
    // https://github.com/gradle/kotlin-dsl-samples/issues/1287#issuecomment-446110725
    private companion object {
        const val androidDynamicFeatureLogic = "android-dynamic-feature-module.gradle"
        const val androidBuildLogic = "android-module.gradle"
        const val kotlinBuildLogic = "kotlin-module.gradle"
    }
}