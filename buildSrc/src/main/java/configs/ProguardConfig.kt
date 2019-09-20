package configs

import java.io.File

class ProguardConfig(private val pathToFiles: String) {

    val customRules by lazy {
        File(pathToFiles).listFiles().toList().toTypedArray()
    }

    companion object {
        const val androidOptimizeProguard = "proguard-android-optimize.txt"
        // DON'T SAFE DELETE THIS CONST
        // This const is used in the .gradle files.
        const val androidDynamicFeatureProguard = "proguard-rules-dynamic-features.pro"
    }

}