package br.com.programadorthi

import kotlinx.coroutines.test.TestCoroutineScope
import org.junit.After

abstract class BaseUnitTest {
    protected val testScope by lazy { TestCoroutineScope() }

    @After
    fun cleanUp() {
        testScope.cleanupTestCoroutines()
    }
}
