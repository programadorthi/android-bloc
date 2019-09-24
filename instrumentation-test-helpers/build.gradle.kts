import dependencies.Libraries
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

    implementation(Libraries.kotlinStdlib)
    implementation(Libraries.coroutinesCore)

    implementation(Libraries.lifecycleViewModelX)

    implementation(Libraries.androidTestRunner)
    implementation(Libraries.espressoCore)
}
