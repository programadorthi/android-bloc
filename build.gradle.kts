import com.vanniktech.android.junit.jacoco.JunitJacocoExtension
import io.gitlab.arturbosch.detekt.detekt
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

buildscript {
    repositories {
        google()
        jcenter()
        maven(url = "https://plugins.gradle.org/m2/")
    }
    dependencies {
        classpath(PluginDependencies.androidSupport)
        classpath(PluginDependencies.kotlinSupport)
        classpath(PluginDependencies.kotlinxSerialization)
        classpath(PluginDependencies.testLogger)
        classpath(PluginDependencies.ktlint)
        classpath(PluginDependencies.jacocoUnified)
        classpath(PluginDependencies.sonarCloud)
        classpath(PluginDependencies.detekt)
    }
}

allprojects {
    repositories {
        google()
        jcenter()
    }

    apply(plugin = PluginIds.detekt)

    detekt {
        config = files("$rootDir/default-detekt-config.yml")
    }

    tasks.withType<KotlinCompile> {
        kotlinOptions.jvmTarget = "1.8"
    }
}

tasks.register("clean").configure {
    delete("build")
}

apply(plugin = PluginIds.jacocoUnified)
apply(plugin = PluginIds.sonarCloud)

configure<JunitJacocoExtension> {
    jacocoVersion = "0.8.4"
}
