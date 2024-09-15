package com.cc.data.mapper

import com.cc.database.model.ExchangeRatesEntity
import com.cc.model.ExchangeRates
import com.cc.network.model.ExchangeRatesResponse

fun ExchangeRatesResponse.toEntity(baseCurrencyCode: String): ExchangeRatesEntity =
    ExchangeRatesEntity(
        baseCurrency = baseCurrencyCode,
        lastlyUpdatedAt = meta.lastlyUpdated,
        exchangeRates = data
    )
