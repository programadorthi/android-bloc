package configs

import modules.ProjectModules
import org.gradle.api.JavaVersion

object AndroidConfig {

    val compileOptionsCompatibility = JavaVersion.VERSION_1_8

    const val applicationId = "br.com.programadorthi.androidbloc"

    const val compileSdkVersion = 29
    const val minSdkVersion = 21
    const val targetSdkVersion = compileSdkVersion

    const val buildToolsVersion = "29.0.2"

    const val instrumentationTestRunner = "androidx.test.runner.AndroidJUnitRunner"

    val generatedDensities = emptyArray<String>()

    val resConfigs = arrayOf("pt-rBR")

    val dynamicFeatures = mutableSetOf(
        ProjectModules.Feature.Counter
    )

    // Used in .gradle files.
    // See android-module.gradle and android-dynamic-feature-module.gradle
    val generatedDensitiesGroovy = java.util.Arrays.asList(*generatedDensities)
}
