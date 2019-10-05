package br.com.programadorthi.timer

import br.com.programadorthi.BaseUnitTest
import br.com.programadorthi.bloc.Bloc
import br.com.programadorthi.timer.bloc.TimerBloc
import br.com.programadorthi.timer.bloc.TimerEvent
import br.com.programadorthi.timer.bloc.TimerState
import kotlinx.coroutines.delay
import kotlinx.coroutines.test.runBlockingTest
import org.assertj.core.api.Assertions.assertThat
import org.junit.Test

import org.junit.Before

class TimerBlocTest : BaseUnitTest() {

    private lateinit var timerBloc: Bloc<TimerEvent, TimerState>

    @Before
    fun `set up`() {
        timerBloc = TimerBloc(testScope)
    }

    @Test
    fun `should initial state be Ready`() {
        assertThat(timerBloc.currentState)
            .isInstanceOf(TimerState.Ready::class.java)
    }

    @Test
    fun `should start event change state to running`() = testScope.runBlockingTest {
        val fiveSeconds = 5000L

        timerBloc.dispatch(TimerEvent.Start(fiveSeconds))

        assertThat(timerBloc.currentState)
            .isInstanceOf(TimerState.Running::class.java)
    }

    @Test
    fun `should have paused state when running and emit pause event`() = testScope.runBlockingTest {
        val fiveSeconds = 5000L

        timerBloc.dispatch(TimerEvent.Start(fiveSeconds))

        assertThat(timerBloc.currentState)
            .isInstanceOf(TimerState.Running::class.java)

        delay(1000L)

        timerBloc.dispatch(TimerEvent.Pause)

        assertThat(timerBloc.currentState)
            .isInstanceOf(TimerState.Paused::class.java)
    }

    @Test
    fun `should have running state again when emit resume event in a paused timer`() = testScope.runBlockingTest {
        val fiveSeconds = 5000L

        timerBloc.dispatch(TimerEvent.Start(fiveSeconds))

        assertThat(timerBloc.currentState)
            .isInstanceOf(TimerState.Running::class.java)

        delay(1000L)

        timerBloc.dispatch(TimerEvent.Pause)

        assertThat(timerBloc.currentState)
            .isInstanceOf(TimerState.Paused::class.java)

        delay(1000L)

        timerBloc.dispatch(TimerEvent.Resume)

        assertThat(timerBloc.currentState)
            .isInstanceOf(TimerState.Running::class.java)
    }

    @Test
    fun `should the pause event affect running timer only`() = testScope.runBlockingTest {
        val previousState = timerBloc.currentState

        timerBloc.dispatch(TimerEvent.Pause)

        assertThat(timerBloc.currentState)
            .isEqualTo(previousState)
    }

    @Test
    fun `should the resume event affect paused timer only`() = testScope.runBlockingTest {
        val previousState = timerBloc.currentState

        timerBloc.dispatch(TimerEvent.Resume)

        assertThat(timerBloc.currentState)
            .isEqualTo(previousState)
    }

    @Test
    fun `should the reset event change timer to initial state`() = testScope.runBlockingTest {
        timerBloc.dispatch(TimerEvent.Reset)

        assertThat(timerBloc.currentState)
            .isInstanceOf(TimerState.Ready::class.java)
    }

    @Test
    fun `should the tick event update timer duration`() = testScope.runBlockingTest {
        val duration = 5000L

        timerBloc.dispatch(TimerEvent.Tick(duration))

        assertThat(timerBloc.currentState)
            .isInstanceOf(TimerState.Running::class.java)
            .hasFieldOrPropertyWithValue("duration", duration)
    }

    @Test
    fun `should the tick event finish timer when time is over`() = testScope.runBlockingTest {
        val duration = 0L

        timerBloc.dispatch(TimerEvent.Tick(duration))

        assertThat(timerBloc.currentState)
            .isInstanceOf(TimerState.Finished::class.java)
    }

}