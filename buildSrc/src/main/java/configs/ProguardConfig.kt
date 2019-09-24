package configs

import java.io.File

class ProguardConfig(private val pathToFiles: String) {

    val customRules by lazy {
        File(pathToFiles).listFiles().toList().toTypedArray()
    }

    companion object {
        const val androidOptimizeProguard = "proguard-android-optimize.txt"
        // DON'T SAFE DELETE CONSTs BELOW
        // They are used in the .gradle files.
        const val androidDynamicFeatureProguard = "proguard-rules-dynamic-features.pro"
        const val consumerProguard = "consumer-rules.pro"
        const val proguardFile = "proguard-rules.pro"
    }

}