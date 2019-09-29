package br.com.programadorthi.login.di

import br.com.programadorthi.androidbloc.AndroidBloc
import br.com.programadorthi.login.LoginUseCase
import br.com.programadorthi.login.UserRepository
import br.com.programadorthi.login.bloc.AuthenticationBloc
import br.com.programadorthi.login.bloc.AuthenticationEvent
import br.com.programadorthi.login.bloc.AuthenticationState
import br.com.programadorthi.login.bloc.LoginBloc
import br.com.programadorthi.login.bloc.LoginEvent
import br.com.programadorthi.login.bloc.LoginState
import br.com.programadorthi.login.data.UserRepositoryImpl
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.core.qualifier.named
import org.koin.dsl.module

const val AUTHENTICATION_BLOC = "AUTHENTICATION_BLOC"
const val LOGIN_BLOC = "LOGIN_BLOC"

val loginModule = module {

    single<UserRepository> { UserRepositoryImpl(get()) }

    single { LoginUseCase(get()) }

    viewModel<AndroidBloc<AuthenticationEvent, AuthenticationState>>(named(AUTHENTICATION_BLOC)) {
        AuthenticationBloc(get(), get())
    }

    viewModel<AndroidBloc<LoginEvent, LoginState>>(named(LOGIN_BLOC)) {
        LoginBloc(get(), get())
    }
}
