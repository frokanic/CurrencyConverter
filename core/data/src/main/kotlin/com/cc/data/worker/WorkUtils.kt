package com.cc.data.worker

import androidx.work.Constraints
import androidx.work.NetworkType

object WorkUtils {

    val DefaultConstraints = Constraints.Builder()
        .setRequiredNetworkType(
            networkType = NetworkType.CONNECTED
        )
        .build()

}