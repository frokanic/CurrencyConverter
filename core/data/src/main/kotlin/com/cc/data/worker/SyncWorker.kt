package com.cc.data.worker

import Synchronizer
import android.content.Context
import androidx.hilt.work.HiltWorker
import androidx.work.WorkerParameters
import androidx.work.CoroutineWorker
import androidx.work.OneTimeWorkRequestBuilder
import com.cc.data.repository.OfflineFirstCurrencyRepository
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject

@HiltWorker
class SyncWorker @AssistedInject constructor(
    @Assisted appContext: Context,
    @Assisted workerParams: WorkerParameters,
    private val currencyRepository: OfflineFirstCurrencyRepository
): CoroutineWorker(appContext, workerParams) {

    override suspend fun doWork(): Result =
        try {
            currencyRepository.syncWith(Synchronizer())
            Result.success()
        } catch (e: Exception) {
            Result.failure()
        }

    companion object {
        const val NAME = "Synchronizer"

        val request = OneTimeWorkRequestBuilder<SyncWorker>()
            .setConstraints(WorkUtils.DefaultConstraints)
            .build()
    }
}