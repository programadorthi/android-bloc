import configs.*
import dependencies.InstrumentationTestsDependencies.Companion.instrumentationTest
import dependencies.Libraries
import dependencies.UnitTestDependencies.Companion.unitTest

plugins {
    id(PluginIds.androidApplication)
    id(PluginIds.kotlinAndroid)
    id(PluginIds.kotlinAndroidExtensions)
}

base.archivesBaseName = "android-bloc-${Versioning.version.name}"

android {

    compileSdkVersion(AndroidConfig.compileSdkVersion)
    buildToolsVersion(AndroidConfig.buildToolsVersion)

    defaultConfig {
        minSdkVersion(AndroidConfig.minSdkVersion)
        targetSdkVersion(AndroidConfig.targetSdkVersion)

        applicationId = AndroidConfig.applicationId
        dynamicFeatures = AndroidConfig.dynamicFeatures
        testInstrumentationRunner = AndroidConfig.instrumentationTestRunner
        versionCode = Versioning.version.code
        versionName = Versioning.version.name

        vectorDrawables.apply {
            useSupportLibrary = true
            generatedDensities(*(AndroidConfig.generatedDensities))
        }

        resConfigs(*(AndroidConfig.resConfigs))
    }

    signingConfigs {
        create("release") {
            storeFile = SigningConfig.storeFile(rootProject)
            storePassword = SigningConfig.storePassword
            keyAlias = SigningConfig.keyAlias
            keyPassword = SigningConfig.keyPassword
        }
    }

    buildTypes {

        getByName("debug") {
            applicationIdSuffix = ".debug"
            versionNameSuffix = "-DEBUG"
            isTestCoverageEnabled = true

            manifestPlaceholders = manifestPlaceholders + FlavorConfig.Manifest.development

            // https://stackoverflow.com/a/55745719
            (this as ExtensionAware).extra["alwaysUpdateBuildId"] = false
        }

        getByName("release") {
            isMinifyEnabled = true

            manifestPlaceholders = manifestPlaceholders + FlavorConfig.Manifest.production

            val proguardConfig = ProguardConfig("$rootDir/proguard")
            proguardFiles(*(proguardConfig.customRules))
            proguardFiles(getDefaultProguardFile(ProguardConfig.androidOptimizeProguard))

            signingConfig = signingConfigs.findByName("release")
        }

    }

    flavorDimensions(*(arrayOf(FlavorConfig.defaultDimensionName)))

    productFlavors {

        create(FlavorConfig.Flavor.development) {
            dimension = FlavorConfig.defaultDimensionName

            buildConfigField("String", "JSONPLACEHOLDER_URL", FlavorConfig.Endpoint.jsonplaceholder)
            buildConfigField("String", "METAWEATHER_URL", FlavorConfig.Endpoint.metaweather)
        }

        create(FlavorConfig.Flavor.production) {
            dimension = FlavorConfig.defaultDimensionName

            buildConfigField("String", "JSONPLACEHOLDER_URL", FlavorConfig.Endpoint.jsonplaceholder)
            buildConfigField("String", "METAWEATHER_URL", FlavorConfig.Endpoint.metaweather)
        }

    }

    compileOptions {
        sourceCompatibility = AndroidConfig.compileOptionsCompatibility
        targetCompatibility = AndroidConfig.compileOptionsCompatibility
    }

}

dependencies {
    implementation(Libraries.kotlinStdlib)

    implementation(Libraries.appCompatX)
    implementation(Libraries.coreAndroidX)
    implementation(Libraries.constraintLayoutX)

    implementation(Libraries.logger)

    unitTest {
        forEachDependency { testImplementation(it) }
    }

    instrumentationTest {
        forEachDependency { androidTestImplementation(it) }
    }
}