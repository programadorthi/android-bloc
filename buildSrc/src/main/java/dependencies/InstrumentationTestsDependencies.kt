package dependencies

import modules.ProjectModules
import org.gradle.api.artifacts.Dependency
import org.gradle.api.artifacts.dsl.DependencyHandler
import org.gradle.kotlin.dsl.project

class InstrumentationTestsDependencies {

    private val all by lazy {
        listOf(
            Libraries.androidTestRules,
            Libraries.androidTestRunner,
            Libraries.androidTestExtJunitKtx,
            Libraries.espressoCore,
            Libraries.espressoContrib,
            Libraries.mockkAndroid,
            Libraries.coroutinesTest
        )
    }

    private val projects by lazy {
        listOf(
            ProjectModules.Test.InstrumentationTestHelpers
        )
    }

    fun forEachDependency(consumer: (String) -> Unit) =
        all.forEach { consumer.invoke(it) }

    fun forEachProjectDependency(handler: DependencyHandler, consumer: (Dependency) -> Unit) =
        projects.forEach { consumer.invoke(handler.project(it)) }

    companion object {
        fun instrumentationTest(block: InstrumentationTestsDependencies.() -> Unit) =
            InstrumentationTestsDependencies().apply(block)
    }
}