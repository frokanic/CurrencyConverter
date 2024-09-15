package com.frokanic.currencyconverter.di

import android.app.Application
import android.content.Context
import androidx.work.Configuration
import androidx.work.ExistingWorkPolicy
import androidx.work.ListenableWorker
import androidx.work.WorkManager
import androidx.work.WorkerFactory
import androidx.work.WorkerParameters
import com.cc.data.repository.OfflineFirstCurrencyRepository
import com.cc.data.worker.SyncWorker
import dagger.hilt.android.HiltAndroidApp
import javax.inject.Inject

@HiltAndroidApp
class CurrencyConverterApp : Application(), Configuration.Provider {

    @Inject
    lateinit var workerFactory: MyHiltWorkerFactory

    override fun onCreate() {
        super.onCreate()

        WorkManager
            .getInstance(this)
            .beginUniqueWork(
                SyncWorker.NAME,
                ExistingWorkPolicy.KEEP,
                SyncWorker.request
            )
            .enqueue()
    }

    override val workManagerConfiguration: Configuration
        get() = Configuration.Builder()
            .setWorkerFactory(
                workerFactory = workerFactory
            )
            .build()
}

class MyHiltWorkerFactory @Inject constructor(
    private val currencyRepository: OfflineFirstCurrencyRepository
) : WorkerFactory() {
    override fun createWorker(
        appContext: Context,
        workerClassName: String,
        workerParameters: WorkerParameters
    ): ListenableWorker? =
        when(workerClassName) {
            SyncWorker::class.java.name ->
                SyncWorker(
                    appContext = appContext,
                    workerParams = workerParameters,
                    currencyRepository = currencyRepository
                )

            else -> null
        }
}