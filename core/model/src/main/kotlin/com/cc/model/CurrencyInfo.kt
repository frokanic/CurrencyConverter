package com.cc.model

import kotlinx.serialization.Serializable

@Serializable
data class CurrencyInfo(
    val code: String,
    val value: Double
)
