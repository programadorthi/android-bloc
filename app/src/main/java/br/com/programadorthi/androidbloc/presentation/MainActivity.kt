package br.com.programadorthi.androidbloc.presentation

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import br.com.programadorthi.androidbloc.R
import br.com.programadorthi.counter.presentation.CounterActivity
import kotlinx.android.synthetic.main.activity_main.startCounterActivity

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        startCounterActivity.setOnClickListener {
            startActivity(activity = CounterActivity::class.java)
        }
    }

    private fun startActivity(activity: Class<*>) {
        val intent = Intent(this, activity)
        startActivity(intent)
    }

}
