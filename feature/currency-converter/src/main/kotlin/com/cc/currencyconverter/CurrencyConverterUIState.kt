package com.cc.currencyconverter

import com.cc.currencyconverter.model.CurrencyUIModel

data class CurrencyConverterUIState(
    val isLoading: Boolean = true,
    val allCurrencies: List<CurrencyUIModel> = emptyList(),
    val fromCurrency: CurrencyUIModel = CurrencyUIModel("", ""),
    val toCurrency: CurrencyUIModel = CurrencyUIModel("", ""),
    val indicativeExchangeRate: String = "",
    val lastlyUpdated: String = ""
) {
    companion object {
        val PreviewData = CurrencyConverterUIState(
            fromCurrency = CurrencyUIModel(
                code = "USD",
                value = "1000.00"
            ),
            toCurrency = CurrencyUIModel(
                code = "USD",
                value = "321.00"
            ),
            indicativeExchangeRate = "1 USD = 1 USD"
        )
    }
}
