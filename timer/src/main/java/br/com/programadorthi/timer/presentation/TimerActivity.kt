package br.com.programadorthi.timer.presentation

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import br.com.programadorthi.bloc.Bloc
import br.com.programadorthi.timer.R
import br.com.programadorthi.timer.bloc.TimerEvent
import br.com.programadorthi.timer.bloc.TimerState
import br.com.programadorthi.timer.di.TIMER_BLOC
import kotlinx.android.synthetic.main.activity_timer.countDownTextView
import kotlinx.android.synthetic.main.activity_timer.playButton
import kotlinx.android.synthetic.main.activity_timer.resetButton
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import org.koin.android.viewmodel.ext.android.viewModel
import org.koin.core.qualifier.named
import java.util.concurrent.TimeUnit

class TimerActivity : AppCompatActivity() {

    private val timerBloc: Bloc<TimerEvent, TimerState> by viewModel(named(TIMER_BLOC))

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_timer)

        lifecycleScope.launch {
            timerBloc.state.collect { state -> handleState(state) }
        }

        playButton.setOnClickListener {
            when (val currentState = timerBloc.currentState) {
                is TimerState.Ready -> {
                    timerBloc.dispatch(TimerEvent.Start(duration = currentState.duration))
                }
                is TimerState.Paused -> {
                    timerBloc.dispatch(TimerEvent.Resume)
                }
                is TimerState.Running -> {
                    timerBloc.dispatch(TimerEvent.Pause)
                }
            }
        }

        resetButton.setOnClickListener {
            timerBloc.dispatch(TimerEvent.Reset)
        }
    }

    private fun handleState(state: TimerState) {
        updateCountDown(state)

        playButton.text = if (state is TimerState.Running) "PAUSE" else "PLAY"
        playButton.visibility = if (state is TimerState.Finished) View.GONE else View.VISIBLE
        resetButton.visibility = if (state is TimerState.Ready) View.GONE else View.VISIBLE
    }

    private fun updateCountDown(state: TimerState) {
        val minutes = TimeUnit.MILLISECONDS.toMinutes(state.duration)
        val seconds = mapSeconds(state)
        val minutesS = minutes.toString().padStart(length = 2, padChar = '0')
        val secondsS = seconds.toString().padStart(length = 2, padChar = '0')

        countDownTextView.text = "$minutesS:$secondsS"
    }

    private fun mapSeconds(state: TimerState): Long {
        val seconds = TimeUnit.MILLISECONDS.toSeconds(state.duration)
        if (seconds < SIXTY_SECONDS) {
            return seconds
        }
        return seconds % SIXTY_SECONDS
    }

    companion object {
        private const val SIXTY_SECONDS = 60L
    }
}