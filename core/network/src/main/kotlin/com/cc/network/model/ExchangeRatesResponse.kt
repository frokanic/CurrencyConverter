package com.cc.network.model

import com.cc.model.CurrencyInfo
import com.cc.model.ExchangeRates
import com.cc.model.Meta
import kotlinx.serialization.Serializable

@Serializable
data class ExchangeRatesResponse(
    val meta: Meta,
    val data: Map<String, CurrencyInfo>
)

fun ExchangeRatesResponse.toExternalModel(
    baseCurrency: String
): ExchangeRates =
    ExchangeRates(
        baseCurrency = baseCurrency,
        rates = data.mapValues { it.value.value },
        lastlyUpdated = meta.lastlyUpdated
    )