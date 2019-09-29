package br.com.programadorthi.counter

import br.com.programadorthi.BaseUnitTest
import br.com.programadorthi.bloc.Bloc
import br.com.programadorthi.counter.bloc.CounterBloc
import br.com.programadorthi.counter.bloc.CounterEvent
import kotlinx.coroutines.test.runBlockingTest
import org.assertj.core.api.Assertions.assertThat
import org.junit.Before
import org.junit.Test

class CounterBlocTest : BaseUnitTest() {

    private lateinit var counterBloc: Bloc<CounterEvent, Int>

    @Before
    fun `set up`() {
        counterBloc = CounterBloc(testScope)
    }

    @Test
    fun `should initial state be 0`() {
        assertThat(counterBloc.currentState).isZero()
    }

    @Test
    fun `should single Increment event updates state to 1`() = testScope.runBlockingTest {
        val expected = 1

        counterBloc.dispatch(CounterEvent.Increment)

        assertThat(counterBloc.currentState).isEqualTo(expected)
    }

    @Test
    fun `should single Decrement event updates state to -1`() = testScope.runBlockingTest {
        val expected = -1

        counterBloc.dispatch(CounterEvent.Decrement)

        assertThat(counterBloc.currentState).isEqualTo(expected)
    }
}