package dependencies

import configs.KotlinConfig

object Libraries {

    // Kotlin
    const val kotlinStdlib = "org.jetbrains.kotlin:kotlin-stdlib-jdk8:${KotlinConfig.version}"

    // Kotlin serialization
    const val kotlinSerialization = "org.jetbrains.kotlinx:kotlinx-serialization-runtime:${Versions.kotlinSerialization}"

    // Kotlin coroutines
    const val coroutinesCore = "org.jetbrains.kotlinx:kotlinx-coroutines-core:${Versions.coroutines}"
    const val coroutinesAndroid = "org.jetbrains.kotlinx:kotlinx-coroutines-android:${Versions.coroutines}"
    const val coroutinesTest = "org.jetbrains.kotlinx:kotlinx-coroutines-test:${Versions.coroutines}"

    // OKHttp and Retrofit
    const val okhttp = "com.squareup.okhttp3:okhttp:${Versions.okHttp}"
    const val okhttpLogger = "com.squareup.okhttp3:logging-interceptor:${Versions.okHttp}"
    const val retrofit = "com.squareup.retrofit2:retrofit:${Versions.retrofit}"
    const val retrofitKotlinSerialization = "com.jakewharton.retrofit:retrofit2-kotlinx-serialization-converter:${Versions.retrofitKotlinSerialization}"

    // Android
    const val appCompatX = "androidx.appcompat:appcompat:${Versions.appCompat}"
    const val coreAndroidX = "androidx.core:core-ktx:${Versions.coreAndroidx}"
    const val constraintLayoutX = "androidx.constraintlayout:constraintlayout:${Versions.constraintlayout}"
    const val lifecycleRuntimeX = "androidx.lifecycle:lifecycle-runtime-ktx:${Versions.lifecycle}"
    const val lifecycleViewModelX = "androidx.lifecycle:lifecycle-viewmodel-ktx:${Versions.lifecycle}"
    const val lifecycleExtensions = "androidx.lifecycle:lifecycle-extensions:${Versions.lifecycle}"

    // Material Design
    const val materialDesign = "com.google.android.material:material:${Versions.materialDesign}"

    // Dependency Injection
    const val koin = "org.koin:koin-android-viewmodel:${Versions.koin}"

    // Logger
    const val logger = "com.orhanobut:logger:${Versions.logger}"

    // Unit test
    const val jUnit = "junit:junit:${Versions.junit}"
    const val assertj = "org.assertj:assertj-core:${Versions.assertj}"
    const val mockk = "io.mockk:mockk:${Versions.mockk}"
    const val roboletric = "org.robolectric:robolectric:${Versions.roboletric}"

    // Instrumentation test
    const val mockkAndroid = "io.mockk:mockk-android:${Versions.mockk}"
    const val androidTestExtJunitKtx = "androidx.test.ext:junit-ktx:${Versions.jUnitKtx}"
    const val androidTestRunner = "androidx.test:runner:${Versions.androidxTest}"
    const val androidTestRules = "androidx.test:rules:${Versions.androidxTest}"
    const val espressoCore = "androidx.test.espresso:espresso-core:${Versions.espresso}"
    const val espressoContrib = "androidx.test.espresso:espresso-contrib:${Versions.espresso}"

    private object Versions {
        const val kotlinSerialization = "0.13.0"

        const val coroutines = "1.3.1"

        const val okHttp = "4.2.0"
        const val retrofit = "2.6.1"
        const val retrofitKotlinSerialization = "0.4.0"

        const val appCompat = "1.1.0"
        const val coreAndroidx = "1.1.0"
        const val constraintlayout = "1.1.3"
        const val lifecycle = "2.1.0"

        const val materialDesign = "1.0.0"

        const val koin = "2.0.1"

        const val logger = "2.2.0"

        const val junit = "4.12"
        const val assertj = "3.11.1"
        const val mockk = "1.9.3"
        const val roboletric = "4.3"

        const val jUnitKtx = "1.1.1"
        const val androidxTest = "1.2.0"
        const val espresso = "3.2.0"
    }
}