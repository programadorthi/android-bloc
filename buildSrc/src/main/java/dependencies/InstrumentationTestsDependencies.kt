package dependencies

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

    fun forEachDependency(consumer: (String) -> Unit) =
        all.forEach { consumer.invoke(it) }

    companion object {
        fun instrumentationTest(block: InstrumentationTestsDependencies.() -> Unit) =
            InstrumentationTestsDependencies().apply(block)
    }
}