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

    implementation(Libraries.lifecycleViewModelX)

    unitTest {
        forEachDependency { testImplementation(it) }
    }

    instrumentationTest {
        forEachDependency { androidTestImplementation(it) }
    }
}