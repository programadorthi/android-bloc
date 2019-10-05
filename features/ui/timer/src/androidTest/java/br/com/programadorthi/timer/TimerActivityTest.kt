package br.com.programadorthi.timer

import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.rule.ActivityTestRule
import br.com.programadorthi.instrumentationtesthelpers.*
import br.com.programadorthi.timer.bloc.TimerEvent
import br.com.programadorthi.timer.bloc.TimerState
import br.com.programadorthi.timer.di.TIMER_BLOC
import br.com.programadorthi.timer.presentation.TimerActivity
import io.mockk.coEvery
import io.mockk.every
import io.mockk.spyk
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.channels.ConflatedBroadcastChannel
import kotlinx.coroutines.flow.asFlow
import org.junit.After

import org.junit.Test
import org.junit.runner.RunWith

import org.junit.Before
import org.junit.Rule
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.core.context.startKoin
import org.koin.core.context.stopKoin
import org.koin.core.qualifier.named
import org.koin.dsl.module

@RunWith(AndroidJUnit4::class)
class TimerActivityTest {

    @get:Rule
    val activityRule = ActivityTestRule(TimerActivity::class.java, true, false)

    private val bloc = spyk(FakeBloc<TimerEvent, TimerState>(
        CoroutineScope(Dispatchers.Main), INITIAL_STATE
    ))

    private lateinit var channel: ConflatedBroadcastChannel<TimerState>

    @Before
    fun setUp() {
        channel = ConflatedBroadcastChannel(INITIAL_STATE)

        coEvery { bloc.state } returns channel.asFlow()

        startKoin {
            modules(module { viewModel(named(TIMER_BLOC)) { bloc } })
        }

        activityRule.launchActivity(null)
    }

    @After
    fun cleanUp() {
        channel.close()
        stopKoin()
    }

    @Test
    fun shouldShowFiveSecondsAndPlayButtonWhenActivityStarted() {
        onViewWithId(R.id.countDownTextView).matchText("00:05")
        onViewWithId(R.id.playButton).matchText("PLAY")
        onViewWithId(R.id.resetButton).isNotDisplayed()
    }

    @Test
    fun shouldShowPauseAndResetButtonsAndTimeDecrementedWhenStartTimer() {
        val startEvent = TimerEvent.Start(FIVE_SECONDS)

        every {
            bloc.dispatch(startEvent)
        } coAnswers {
            channel.send(TimerState.Running(FIVE_SECONDS - ONE_SECOND))
        }

        bloc.dispatch(startEvent)

        onViewWithId(R.id.countDownTextView).matchText("00:04")
        onViewWithId(R.id.playButton).matchText("PAUSE")
        onViewWithId(R.id.resetButton).isDisplayed()
    }

    @Test
    fun shouldShowPlayAndResetButtonsAndTimePausedWhenPauseTimer() {
        every {
            bloc.dispatch(TimerEvent.Pause)
        } coAnswers {
            channel.send(TimerState.Paused(FIVE_SECONDS - (ONE_SECOND * 2)))
        }

        bloc.dispatch(TimerEvent.Pause)

        onViewWithId(R.id.countDownTextView).matchText("00:03")
        onViewWithId(R.id.playButton).matchText("PLAY")
        onViewWithId(R.id.resetButton).isDisplayed()
    }

    @Test
    fun shouldShowPlayButtonAndTimeRestartedWhenResetTimer() {
        every {
            bloc.dispatch(TimerEvent.Reset)
        } coAnswers {
            channel.send(TimerState.Ready(FIVE_SECONDS))
        }

        bloc.dispatch(TimerEvent.Reset)

        onViewWithId(R.id.countDownTextView).matchText("00:05")
        onViewWithId(R.id.playButton).matchText("PLAY")
        onViewWithId(R.id.resetButton).isNotDisplayed()
    }

    @Test
    fun shouldShowPauseButtonAndTimeResumedWhenResumeTimer() {
        every {
            bloc.dispatch(TimerEvent.Resume)
        } coAnswers {
            channel.send(TimerState.Running(FIVE_SECONDS - (ONE_SECOND * 3)))
        }

        bloc.dispatch(TimerEvent.Resume)

        onViewWithId(R.id.countDownTextView).matchText("00:02")
        onViewWithId(R.id.playButton).matchText("PAUSE")
        onViewWithId(R.id.resetButton).isDisplayed()
    }

    @Test
    fun shouldShowResetButtonAndTimeFinishedWhenFinishTimer() {
        val tickerEvent = TimerEvent.Tick(0L)

        every {
            bloc.dispatch(tickerEvent)
        } coAnswers {
            channel.send(TimerState.Finished)
        }

        bloc.dispatch(tickerEvent)

        onViewWithId(R.id.countDownTextView).matchText("00:00")
        onViewWithId(R.id.playButton).isNotDisplayed()
        onViewWithId(R.id.resetButton).isDisplayed()
    }

    private companion object {
        private const val ONE_SECOND = 1000L
        private const val FIVE_SECONDS = ONE_SECOND * 5
        private val INITIAL_STATE = TimerState.Ready(FIVE_SECONDS)
    }
}