package br.com.programadorthi.login.presentation

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import br.com.programadorthi.androidbloc.AndroidBloc
import br.com.programadorthi.login.R
import br.com.programadorthi.login.bloc.AuthenticationEvent
import br.com.programadorthi.login.bloc.AuthenticationState
import br.com.programadorthi.login.di.AUTHENTICATION_BLOC
import kotlinx.android.synthetic.main.activity_home.logoutTextView
import kotlinx.android.synthetic.main.activity_home.progressBar
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import org.koin.android.viewmodel.ext.android.viewModel
import org.koin.core.qualifier.named

class HomeActivity : AppCompatActivity() {

    private val authenticationBloc: AndroidBloc<AuthenticationEvent, AuthenticationState>
            by viewModel(named(AUTHENTICATION_BLOC))

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        logoutTextView.setOnClickListener {
            authenticationBloc.dispatch(AuthenticationEvent.LoggedOut)
        }

        lifecycleScope.launch {
            authenticationBloc.state.collect { state ->
                when (state) {
                    is AuthenticationState.AuthenticationUnauthenticated -> finish()
                    is AuthenticationState.AuthenticationLoading -> {
                        logoutTextView.visibility = View.GONE
                        progressBar.visibility = View.VISIBLE
                    }
                }
            }
        }
    }
}
