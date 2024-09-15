package com.cc.database.converter

import androidx.room.TypeConverter
import com.cc.model.CurrencyInfo

class CurrencyRatesConverter {

    @TypeConverter
    fun fromCurrenciesMapToString(currencies: Map<String, CurrencyInfo>): String {
        var result = ""

        currencies.forEach {
            result += "${it.key}:${it.value.code},${it.value.value}/"
        }

        return result
    }

    @TypeConverter
    fun fromCurrenciesStringToMap(string: String): Map<String, CurrencyInfo> {
        val result = mutableMapOf<String, CurrencyInfo>()
        val mapEntries = string.split("/")

        for (mapEntry in mapEntries) {
            val keyValue = mapEntry.split(":")
            val key = keyValue.firstOrNull() ?: break
            val value = keyValue.getOrNull(index = 1)?.split(",") ?: break

            val currencyInfo = CurrencyInfo(
                code = value.first(),
                value = value.last().toDouble()
            )

            result[key] = currencyInfo
        }

        return result
    }

}