package br.com.programadorthi.infinitelist.di

import br.com.programadorthi.bloc.Bloc
import br.com.programadorthi.infinitelist.bloc.PostBloc
import br.com.programadorthi.infinitelist.bloc.PostEvent
import br.com.programadorthi.infinitelist.bloc.PostState
import br.com.programadorthi.infinitelist.data.PostService
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import kotlinx.serialization.json.Json
import okhttp3.MediaType
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.core.qualifier.named
import org.koin.dsl.module
import retrofit2.Retrofit

const val POST_BLOC = "POST_BLOC"

private const val RETROFIT_INFINITE_LIST = "RETROFIT_INFINITE_LIST"

private val contentType = MediaType.get("application/json")

val postModule = module {

    single<Retrofit>(named(RETROFIT_INFINITE_LIST)) {
        Retrofit.Builder()
            .baseUrl("https://jsonplaceholder.typicode.com")
            .addConverterFactory(Json.asConverterFactory(contentType))
            .build()
    }

    single<PostService> {
        get<Retrofit>(named(RETROFIT_INFINITE_LIST)).create(PostService::class.java)
    }

    viewModel<Bloc<PostEvent, PostState>>(named(POST_BLOC)) { PostBloc(get(), get()) }

}