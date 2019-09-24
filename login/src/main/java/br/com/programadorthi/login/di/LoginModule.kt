package br.com.programadorthi.login.di

import br.com.programadorthi.bloc.Bloc
import br.com.programadorthi.login.bloc.AuthenticationBloc
import br.com.programadorthi.login.bloc.AuthenticationEvent
import br.com.programadorthi.login.bloc.AuthenticationState
import br.com.programadorthi.login.bloc.LoginBloc
import br.com.programadorthi.login.bloc.LoginEvent
import br.com.programadorthi.login.bloc.LoginState
import br.com.programadorthi.login.data.UserRepository
import br.com.programadorthi.login.domain.LoginUseCase
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.core.qualifier.named
import org.koin.dsl.module

const val AUTHENTICATION_BLOC = "AUTHENTICATION_BLOC"
const val LOGIN_BLOC = "LOGIN_BLOC"

val loginModule = module {

    single { UserRepository(get()) }

    single { LoginUseCase(get()) }

    viewModel<Bloc<AuthenticationEvent, AuthenticationState>>(named(AUTHENTICATION_BLOC)) {
        AuthenticationBloc(get(), get())
    }

    viewModel<Bloc<LoginEvent, LoginState>>(named(LOGIN_BLOC)) {
        LoginBloc(get(), get())
    }

}