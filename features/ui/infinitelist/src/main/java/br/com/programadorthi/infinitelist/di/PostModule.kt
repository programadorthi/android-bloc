package br.com.programadorthi.infinitelist.di

import br.com.programadorthi.androidbloc.AndroidBloc
import br.com.programadorthi.infinitelist.PostService
import br.com.programadorthi.infinitelist.RetrofitBuilder
import br.com.programadorthi.infinitelist.bloc.PostBloc
import br.com.programadorthi.infinitelist.bloc.PostEvent
import br.com.programadorthi.infinitelist.bloc.PostState
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.core.qualifier.named
import org.koin.dsl.module
import retrofit2.Retrofit

const val POST_BLOC = "POST_BLOC"

private const val RETROFIT_INFINITE_LIST = "RETROFIT_INFINITE_LIST"

val postModule = module {

    single(named(RETROFIT_INFINITE_LIST)) {
        RetrofitBuilder.provide()
    }

    single {
        get<Retrofit>(named(RETROFIT_INFINITE_LIST)).create(PostService::class.java)
    }

    viewModel<AndroidBloc<PostEvent, PostState>>(named(POST_BLOC)) { PostBloc(get(), get()) }
}
