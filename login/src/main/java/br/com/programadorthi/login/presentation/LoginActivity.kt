package br.com.programadorthi.login.presentation

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import br.com.programadorthi.bloc.Bloc
import br.com.programadorthi.login.R
import br.com.programadorthi.login.bloc.LoginEvent
import br.com.programadorthi.login.bloc.LoginState
import br.com.programadorthi.login.di.LOGIN_BLOC
import kotlinx.android.synthetic.main.activity_login.button
import kotlinx.android.synthetic.main.activity_login.passwordEditText
import kotlinx.android.synthetic.main.activity_login.progressBar
import kotlinx.android.synthetic.main.activity_login.usernameEditText
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import org.koin.android.viewmodel.ext.android.viewModel
import org.koin.core.qualifier.named

class LoginActivity : AppCompatActivity() {

    private val loginBloc: Bloc<LoginEvent, LoginState> by viewModel(named(LOGIN_BLOC))

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        initStateObservers()

        button.setOnClickListener {
            hideKeyboard()
            loginBloc.dispatch(
                LoginEvent.LoginButtonPressed(
                    username = usernameEditText.text.toString(),
                    password = passwordEditText.text.toString()
                )
            )
        }
    }

    private fun hideKeyboard() {
        val token = currentFocus?.windowToken ?: return
        val inputMethodManager =
            getSystemService(Context.INPUT_METHOD_SERVICE) as? InputMethodManager ?: return
        inputMethodManager.hideSoftInputFromWindow(token, 0)
    }

    private fun initStateObservers() {
        lifecycleScope.launch {
            loginBloc.state.collect { state ->
                when (state) {
                    is LoginState.LoginFailed -> {
                        Toast.makeText(
                            this@LoginActivity,
                            "Usuário não identificado. Tente novamente!",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                    is LoginState.LoginFailure -> {
                        Toast.makeText(
                            this@LoginActivity,
                            state.exception.message,
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                    is LoginState.LoginSuccess -> {
                        startActivity(Intent(this@LoginActivity, HomeActivity::class.java))
                        finish()
                    }
                }

                button.visibility =
                    if (state is LoginState.LoginLoading) View.GONE else View.VISIBLE
                progressBar.visibility =
                    if (state !is LoginState.LoginLoading) View.GONE else View.VISIBLE
            }
        }
    }

}