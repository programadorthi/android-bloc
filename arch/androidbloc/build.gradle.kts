import dependencies.Libraries
import dependencies.UnitTestDependencies.Companion.unitTest
import modules.LibraryModule
import modules.LibraryType
import modules.ProjectModules

val module = LibraryModule(rootDir, LibraryType.AndroidLight)

apply(from = module.script())

plugins {
    id(PluginIds.androidLibrary)
}

dependencies {
    implementation(project(ProjectModules.Arch.Bloc))

    implementation(Libraries.kotlinStdlib)
    implementation(Libraries.coroutinesCore)

    implementation(Libraries.lifecycleViewModelX)

    unitTest {
        forEachDependency { testImplementation(it) }
    }
}