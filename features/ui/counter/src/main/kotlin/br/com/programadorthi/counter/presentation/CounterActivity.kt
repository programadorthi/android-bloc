package br.com.programadorthi.counter.presentation

import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import android.os.Bundle
import br.com.programadorthi.androidbloc.AndroidBloc
import br.com.programadorthi.counter.R
import br.com.programadorthi.counter.bloc.CounterEvent
import br.com.programadorthi.counter.di.COUNTER_BLOC
import kotlinx.android.synthetic.main.activity_counter.counterTextView
import kotlinx.android.synthetic.main.activity_counter.decrementButton
import kotlinx.android.synthetic.main.activity_counter.incrementButton
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import org.koin.android.viewmodel.ext.android.viewModel
import org.koin.core.qualifier.named

class CounterActivity : AppCompatActivity() {

    private val counterBloc: AndroidBloc<CounterEvent, Int> by viewModel(named(COUNTER_BLOC))

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_counter)

        lifecycleScope.launch {
            counterBloc.state.collect { state ->
                counterTextView.text = "Counter: $state"
            }
        }

        decrementButton.setOnClickListener {
            counterBloc.dispatch(CounterEvent.Decrement)
        }

        incrementButton.setOnClickListener {
            counterBloc.dispatch(CounterEvent.Increment)
        }
    }
}
