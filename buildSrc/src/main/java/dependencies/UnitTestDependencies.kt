package dependencies

class UnitTestDependencies {

    private val all by lazy {
        listOf(
            Libraries.jUnit,
            Libraries.assertj,
            Libraries.mockk,
            Libraries.coroutinesTest
        )
    }

    fun forEachDependency(consumer: (String) -> Unit) =
        all.forEach { consumer.invoke(it) }

    companion object {
        fun unitTest(block: UnitTestDependencies.() -> Unit) =
            UnitTestDependencies().apply(block)
    }
}