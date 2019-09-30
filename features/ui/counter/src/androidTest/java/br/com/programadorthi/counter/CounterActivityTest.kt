package br.com.programadorthi.counter

import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.rule.ActivityTestRule
import br.com.programadorthi.counter.bloc.CounterEvent
import br.com.programadorthi.counter.di.COUNTER_BLOC
import br.com.programadorthi.counter.presentation.CounterActivity
import br.com.programadorthi.instrumentationtesthelpers.FakeBloc
import br.com.programadorthi.instrumentationtesthelpers.matchText
import br.com.programadorthi.instrumentationtesthelpers.onViewWithId
import io.mockk.coEvery
import io.mockk.every
import io.mockk.spyk
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.channels.ConflatedBroadcastChannel
import kotlinx.coroutines.flow.asFlow
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.core.context.startKoin
import org.koin.core.context.stopKoin
import org.koin.core.qualifier.named
import org.koin.dsl.module

@RunWith(AndroidJUnit4::class)
class CounterActivityTest {

    @get:Rule
    val activityRule = ActivityTestRule(CounterActivity::class.java, true, false)

    private val bloc = spyk(FakeBloc<CounterEvent, Int>(CoroutineScope(Dispatchers.Main), 0))

    private lateinit var channel: ConflatedBroadcastChannel<Int>

    @Before
    fun setUp() {
        println(">>>>>>>>>>>>> setUp")
        channel = ConflatedBroadcastChannel(0)

        coEvery { bloc.state } returns channel.asFlow()

        startKoin {
            modules(module { viewModel(named(COUNTER_BLOC)) { bloc } })
        }

        activityRule.launchActivity(null)
    }

    @After
    fun cleanUp() {
        channel.close()
        stopKoin()
        println(">>>>>>>>>>>>> cleanUp")
    }

    @Test
    fun shouldShowZeroCounterTextWhenInitialStateIsZero() {
        onViewWithId(R.id.counterTextView).matchText("Counter: 0")
    }

    @Test
    fun shouldShowMinusOneCounterTextWhenDispatcherDecrement() {
        every {
            bloc.dispatch(CounterEvent.Decrement)
        } coAnswers {
            channel.send(channel.value - 1)
        }

        bloc.dispatch(CounterEvent.Decrement)

        onViewWithId(R.id.counterTextView).matchText("Counter: -1")
    }

    @Test
    fun shouldShowOneCounterTextWhenDispatcherIncrement() {
        every {
            bloc.dispatch(CounterEvent.Increment)
        } coAnswers {
            channel.send(channel.value + 1)
        }

        bloc.dispatch(CounterEvent.Increment)

        onViewWithId(R.id.counterTextView).matchText("Counter: 1")
    }
}