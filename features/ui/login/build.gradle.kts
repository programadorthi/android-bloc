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
}

dependencies {
    implementation(project(ProjectModules.Arch.Bloc))
    implementation(project(ProjectModules.Arch.AndroidBloc))
    implementation(project(ProjectModules.Feature.Domain.Login))

    implementation(Libraries.kotlinStdlib)
    implementation(Libraries.coroutinesCore)

    implementation(Libraries.appCompatX)
    implementation(Libraries.constraintLayoutX)

    implementation(Libraries.lifecycleRuntimeX)

    implementation(Libraries.koin)

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
