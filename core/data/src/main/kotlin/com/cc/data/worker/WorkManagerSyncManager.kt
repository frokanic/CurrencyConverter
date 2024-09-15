package com.cc.data.worker

import android.content.Context
import androidx.work.WorkInfo
import androidx.work.WorkManager
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

interface SyncManager {
    val isSyncing: Flow<Boolean>
}

class WorkManagerSyncManager @Inject constructor(
    @ApplicationContext private val appContext: Context
): SyncManager {

    override val isSyncing: Flow<Boolean>
        get() = WorkManager
            .getInstance(appContext)
            .getWorkInfosForUniqueWorkFlow(SyncWorker.NAME)
            .map { it.anyRunning() }

}

private fun List<WorkInfo>.anyRunning(): Boolean =
    any { it.state == WorkInfo.State.RUNNING }