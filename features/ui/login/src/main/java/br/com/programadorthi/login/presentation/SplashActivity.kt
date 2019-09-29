package br.com.programadorthi.login.presentation

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import br.com.programadorthi.androidbloc.AndroidBloc
import br.com.programadorthi.login.R
import br.com.programadorthi.login.bloc.AuthenticationEvent
import br.com.programadorthi.login.bloc.AuthenticationState
import br.com.programadorthi.login.di.AUTHENTICATION_BLOC
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import org.koin.android.viewmodel.ext.android.viewModel
import org.koin.core.qualifier.named

class SplashActivity : AppCompatActivity() {

    private val authenticationBloc: AndroidBloc<AuthenticationEvent, AuthenticationState>
            by viewModel(named(AUTHENTICATION_BLOC))

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        lifecycleScope.launch {
            delay(DELAY)

            authenticationBloc.state.collect { state ->
                when (state) {
                    is AuthenticationState.AuthenticationUnauthenticated -> {
                        startActivity(Intent(this@SplashActivity, LoginActivity::class.java))
                    }
                    is AuthenticationState.AuthenticationAuthenticated -> {
                        startActivity(Intent(this@SplashActivity, HomeActivity::class.java))
                    }
                }
            }
        }
    }

    override fun onResume() {
        super.onResume()
        authenticationBloc.dispatch(AuthenticationEvent.AppStarted)
    }

    override fun onPause() {
        authenticationBloc.dispatch(AuthenticationEvent.AppStopped)
        super.onPause()
    }

    private companion object {
        private const val DELAY = 2000L
    }
}
