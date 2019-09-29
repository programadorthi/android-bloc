package modules

sealed class LibraryType {
    object Kotlin : LibraryType()
    object Android : LibraryType()
    object AndroidLight : LibraryType()
    object DynamicFeature : LibraryType()
}