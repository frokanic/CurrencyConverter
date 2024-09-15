package com.cc.data.repository

import Syncable
import Synchronizer
import com.cc.data.mapper.toEntity
import com.cc.database.db.CurrencyDatabase
import com.cc.database.model.asExternalModel
import com.cc.model.ExchangeRates
import com.cc.network.api.CurrencyApi
import com.cc.network.model.toExternalModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

interface OfflineFirstCurrencyRepository: Syncable {
    fun getExchangeRates(): Flow<ExchangeRates>
}

class OfflineFirstCurrencyRepositoryImpl(
    database: CurrencyDatabase,
    private val api: CurrencyApi
) : OfflineFirstCurrencyRepository {

    private val dao = database.dao()

    override fun getExchangeRates(): Flow<ExchangeRates> =
        dao.getExchangeRates().map {
            it.asExternalModel()
        }

    override suspend fun syncWith(synchronizer: Synchronizer) {
        synchronizer.start(
            fetchRemoteData = { api.getExchangeRates() },
            deleteLocalData = { dao.deleteExchangeRates() },
            updateLocalData = {
                dao.upsertExchangeRates(
                    it.toEntity(
                        baseCurrencyCode = "USD"
                    )
                )
            }
        )
    }

}