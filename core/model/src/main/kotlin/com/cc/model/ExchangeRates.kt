package com.cc.model

data class ExchangeRates(
    val baseCurrency: String,
    val rates: Map<String, Double>,
    val lastlyUpdated: String
)
