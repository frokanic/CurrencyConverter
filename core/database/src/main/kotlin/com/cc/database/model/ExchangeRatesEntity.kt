package com.cc.database.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.cc.model.CurrencyInfo
import com.cc.model.ExchangeRates

@Entity
data class ExchangeRatesEntity(
    @PrimaryKey
    val lastlyUpdatedAt: String,
    val baseCurrency: String,
    val exchangeRates: Map<String, CurrencyInfo>
)

fun ExchangeRatesEntity.asExternalModel() =
    ExchangeRates(
        baseCurrency = baseCurrency,
        rates = exchangeRates.mapValues { it.value.value },
        lastlyUpdated = lastlyUpdatedAt
    )