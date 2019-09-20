package configs

import org.gradle.api.Project
import java.io.File

object SigningConfig {
    const val keyAlias = "keyAlias"
    const val keyPassword = "keyPassword"
    const val storePassword = "storePassword"
    private const val storeFile = "/path/to/file.jks"

    @JvmStatic
    fun storeFile(rootProject: Project): File {
        return rootProject.file(storeFile)
    }
}