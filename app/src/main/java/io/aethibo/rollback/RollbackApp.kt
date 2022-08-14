package io.aethibo.rollback

import android.app.Application
import io.aethibo.rollback.framework.di.*
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin
import timber.log.Timber

class RollbackApp : Application() {

    companion object {
        lateinit var instance: Application
    }

    init {
        instance = this
    }

    override fun onCreate() {
        super.onCreate()

        if (BuildConfig.DEBUG)
            Timber.plant(Timber.DebugTree())

        setupKoin()
    }

    private fun setupKoin() {
        startKoin {
            androidContext(applicationContext)
            modules(
                networkModule,
                dataSourceModule,
                repositoriesModule,
                useCasesModule,
                viewModelsModule
            )
        }
    }
}