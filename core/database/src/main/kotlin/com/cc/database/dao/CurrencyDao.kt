package com.cc.database.dao

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Upsert
import com.cc.database.model.ExchangeRatesEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface CurrencyDao {

    @Upsert
    suspend fun upsertExchangeRates(currencies: ExchangeRatesEntity)

    @Query("DELETE FROM ExchangeRatesEntity")
    suspend fun deleteExchangeRates()

    @Query("SELECT * FROM ExchangeRatesEntity")
    fun getExchangeRates(): Flow<ExchangeRatesEntity>
}