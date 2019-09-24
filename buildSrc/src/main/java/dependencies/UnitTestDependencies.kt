package dependencies

import modules.ProjectModules
import org.gradle.api.artifacts.Dependency
import org.gradle.api.artifacts.dsl.DependencyHandler
import org.gradle.kotlin.dsl.project

class UnitTestDependencies {

    private val all by lazy {
        listOf(
            Libraries.jUnit,
            Libraries.assertj,
            Libraries.mockk,
            Libraries.coroutinesTest
        )
    }

    private val projects by lazy {
        listOf(
            ProjectModules.Test.UnitTestHelpers
        )
    }

    fun forEachDependency(consumer: (String) -> Unit) =
        all.forEach { consumer.invoke(it) }

    fun forEachProjectDependency(handler: DependencyHandler, consumer: (Dependency) -> Unit) =
        projects.forEach { consumer.invoke(handler.project(it)) }

    companion object {
        fun unitTest(block: UnitTestDependencies.() -> Unit) =
            UnitTestDependencies().apply(block)
    }
}