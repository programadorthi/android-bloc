package modules

import modules.LibraryType.Android
import modules.LibraryType.AndroidLight
import modules.LibraryType.DynamicFeature
import modules.LibraryType.Kotlin
import java.io.File

class LibraryModule(
    private val rootDir: File,
    private val type: LibraryType
) {

    fun script() = "$rootDir/buildSrc/shared/${target()}"

    private fun target() = when (type) {
        Kotlin -> KOTLIN_MODULE
        Android -> ANDROID_MODULE
        AndroidLight -> ANDROID_LIGHT_MODULE
        DynamicFeature -> ANDROID_DYNAMIC_FEATURE_MODULE
    }

    // Why using .gradle instead of .gradle.kts files:
    // https://github.com/gradle/kotlin-dsl-samples/issues/1287#issuecomment-446110725
    private companion object {
        private const val ANDROID_DYNAMIC_FEATURE_MODULE = "android-dynamic-feature-module.gradle"
        private const val ANDROID_MODULE = "android-module.gradle"
        private const val ANDROID_LIGHT_MODULE = "android-module-light.gradle"
        private const val KOTLIN_MODULE = "kotlin-module.gradle"
    }
}