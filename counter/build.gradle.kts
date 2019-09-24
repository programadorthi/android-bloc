import dependencies.InstrumentationTestsDependencies.Companion.instrumentationTest
import dependencies.Libraries
import dependencies.UnitTestDependencies.Companion.unitTest
import modules.LibraryModule
import modules.LibraryType
import modules.ProjectModules

val module = LibraryModule(rootDir, LibraryType.DynamicFeature)

apply(from = module.script())

plugins {
    id(PluginIds.androidDynamicFeature)
}

dependencies {
    implementation(project(ProjectModules.App))
    implementation(project(ProjectModules.Arch.Bloc))

    implementation(Libraries.kotlinStdlib)
    implementation(Libraries.coroutinesCore)

    implementation(Libraries.appCompatX)
    implementation(Libraries.constraintLayoutX)

    implementation(Libraries.lifecycleRuntimeX)

    implementation(Libraries.koin)

    implementation(Libraries.logger)

    unitTest {
        forEachDependency { testImplementation(it) }
    }

    instrumentationTest {
        forEachDependency { androidTestImplementation(it) }
    }
}
