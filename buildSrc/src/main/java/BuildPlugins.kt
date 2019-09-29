import configs.KotlinConfig

private object Versions {
    const val androidGradlePlugin = "3.5.0"
    const val testLogger = "1.7.0"
    const val ktlint = "8.2.0"
    const val jacocoUnified = "0.15.0"
    const val sonarCloud = "2.7.1"
    const val detekt = "1.0.1"
}

object PluginDependencies {
    const val androidSupport = "com.android.tools.build:gradle:${Versions.androidGradlePlugin}"
    const val testLogger = "com.adarshr:gradle-test-logger-plugin:${Versions.testLogger}"
    const val ktlint = "org.jlleitschuh.gradle:ktlint-gradle:${Versions.ktlint}"
    const val kotlinSupport = "org.jetbrains.kotlin:kotlin-gradle-plugin:${KotlinConfig.version}"
    const val kotlinxSerialization = "org.jetbrains.kotlin:kotlin-serialization:${KotlinConfig.version}"
    const val jacocoUnified = "com.vanniktech:gradle-android-junit-jacoco-plugin:${Versions.jacocoUnified}"
    const val sonarCloud = "org.sonarsource.scanner.gradle:sonarqube-gradle-plugin:${Versions.sonarCloud}"
    const val detekt = "io.gitlab.arturbosch.detekt:detekt-gradle-plugin:${Versions.detekt}"
}

object PluginIds {
    const val androidApplication = "com.android.application"
    const val androidLibrary = "com.android.library"
    const val kotlinJVM = "kotlin"
    const val kotlinxSerialization = "kotlinx-serialization"
    const val kotlinAndroid = "kotlin-android"
    const val kotlinAndroidExtensions = "kotlin-android-extensions"
    const val jacocoUnified = "com.vanniktech.android.junit.jacoco"
    const val sonarCloud = "org.sonarqube"
    const val detekt = "io.gitlab.arturbosch.detekt"
    // Plugins below are used in .gradle files. Don't remove!!!!
    const val androidDynamicFeature = "com.android.dynamic-feature"
    const val testLogger = "com.adarshr.test-logger"
    const val ktlint = "org.jlleitschuh.gradle.ktlint"
}
