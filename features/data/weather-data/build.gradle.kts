import dependencies.Libraries
import modules.LibraryModule
import modules.LibraryType

val module = LibraryModule(rootDir, LibraryType.Kotlin)

apply(from = module.script())

plugins {
    id(PluginIds.kotlinJVM)
    id(PluginIds.kotlinxSerialization)
}

dependencies {
    implementation(Libraries.kotlinStdlib)
    implementation(Libraries.kotlinSerialization)
    implementation(Libraries.coroutinesCore)

    implementation(Libraries.retrofit)
    implementation(Libraries.retrofitKotlinSerialization)
}
