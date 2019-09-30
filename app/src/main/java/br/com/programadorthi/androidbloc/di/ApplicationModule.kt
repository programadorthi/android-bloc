package br.com.programadorthi.androidbloc.di

import androidx.preference.PreferenceManager
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import org.koin.dsl.module

val appModule = module {

    single { PreferenceManager.getDefaultSharedPreferences(get()) }

    factory { CoroutineScope(Dispatchers.Default) }

}
