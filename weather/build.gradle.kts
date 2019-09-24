import dependencies.InstrumentationTestsDependencies.Companion.instrumentationTest
import dependencies.Libraries
import dependencies.UnitTestDependencies.Companion.unitTest
import modules.LibraryModule
import modules.LibraryType
import modules.ProjectModules

val module = LibraryModule(rootDir, LibraryType.Android)

apply(from = module.script())

plugins {
    id(PluginIds.androidLibrary)
    id(PluginIds.kotlinxSerialization)
}

dependencies {
    implementation(project(ProjectModules.Arch.Bloc))

    implementation(Libraries.kotlinStdlib)
    implementation(Libraries.kotlinSerialization)
    implementation(Libraries.coroutinesCore)

    implementation(Libraries.appCompatX)
    implementation(Libraries.constraintLayoutX)
    implementation(Libraries.preferenceX)

    implementation(Libraries.lifecycleRuntimeX)

    implementation(Libraries.koin)

    implementation(Libraries.retrofit)
    implementation(Libraries.retrofitKotlinSerialization)

    implementation(Libraries.logger)

    unitTest {
        forEachDependency { testImplementation(it) }

        forEachProjectDependency(this@dependencies) {
            testImplementation(it)
        }
    }

    instrumentationTest {
        forEachDependency { androidTestImplementation(it) }
    }
}
