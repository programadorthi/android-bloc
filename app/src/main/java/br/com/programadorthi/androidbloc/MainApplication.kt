package br.com.programadorthi.androidbloc

import android.app.Application
import br.com.programadorthi.androidbloc.bloc.MainBloc
import br.com.programadorthi.androidbloc.di.appModule
import br.com.programadorthi.bloc.BlocInterceptor
import br.com.programadorthi.counter.di.counterModule
import br.com.programadorthi.infinitelist.di.postModule
import br.com.programadorthi.login.di.loginModule
import br.com.programadorthi.timer.di.timerModule
import com.orhanobut.logger.AndroidLogAdapter
import com.orhanobut.logger.Logger
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin

class MainApplication : Application() {
    override fun onCreate() {
        super.onCreate()

        Logger.addLogAdapter(object : AndroidLogAdapter() {
            override fun isLoggable(priority: Int, tag: String?): Boolean = BuildConfig.DEBUG
        })

        BlocInterceptor.initBlocInterceptor(MainBloc())

        // Start Koin
        startKoin {
            androidLogger()
            androidContext(this@MainApplication)
            modules(listOf(appModule, counterModule, loginModule, postModule, timerModule))
        }
    }
}