package com.cc.currencyconverter

import android.content.SharedPreferences
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.cc.currencyconverter.model.CurrencyUIModel
import com.cc.data.repository.OfflineFirstCurrencyRepository
import com.cc.data.worker.WorkManagerSyncManager
import com.cc.model.CurrencyInfo
import com.cc.model.ExchangeRates
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.retryWhen
import kotlinx.coroutines.flow.update
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import javax.inject.Inject

@HiltViewModel
class CurrencyConverterViewModel @Inject constructor(
    private val offlineFirstCurrencyRepository: OfflineFirstCurrencyRepository,
    private val sharedPreferences: SharedPreferences,
    workManagerSyncManager: WorkManagerSyncManager
) : ViewModel() {

    private val _uiState = MutableStateFlow(value = CurrencyConverterUIState())
    val uiState = _uiState.asStateFlow()

    private lateinit var exchangeRates: ExchangeRates

    private companion object {
        const val FROM_CURRENCY_KEY = "fromCurrencyKey"
        const val TO_CURRENCY_KEY = "toCurrencyKey"
    }

    init {
        workManagerSyncManager
            .isSyncing
            .onEach { isLoading ->
                _uiState.update {
                    it.copy(
                        isLoading = isLoading
                    )
                }
            }
            .launchIn(
                scope = viewModelScope
            )

        initUIState()
    }

    private fun initUIState() {
       offlineFirstCurrencyRepository
            .getExchangeRates()
           .retryWhen { cause, _ ->
               cause is IllegalStateException
           }
            .onEach { exchangeRates ->
                if(exchangeRates.rates.isEmpty()) {
                    return@onEach
                }

                this.exchangeRates = exchangeRates

                _uiState.update {
                    it.copy(
                        isLoading = false,
                        allCurrencies = exchangeRates.rates.keys.map { code ->
                            CurrencyUIModel(
                                code = code,
                                value = ""
                            )
                        },
                        lastlyUpdated = fromDate(date = exchangeRates.lastlyUpdated)
                    )
                }

                setInitialCurrencies()
            }
            .launchIn(
                scope = viewModelScope
            )
    }

    private fun setInitialCurrencies() {
        val initialCurrencies = getUserCurrencies()

        val convertedResult = convert(
            fromBaseValue = initialCurrencies.first.value,
            toBaseValue = initialCurrencies.second.value,
            amount = 1.0
        )

        _uiState.update {
            it.copy(
                fromCurrency = CurrencyUIModel(
                    code = initialCurrencies.first.code,
                    value = "1.00"
                ),
                toCurrency = CurrencyUIModel(
                    code = initialCurrencies.second.code,
                    value = convertedResult
                ),
                indicativeExchangeRate = "1 ${initialCurrencies.first.code} = $convertedResult ${initialCurrencies.second.code}"
            )
        }
    }

    private fun getUserCurrencies(): Pair<CurrencyInfo, CurrencyInfo> {
        val fromCurrencyCode = sharedPreferences
            .getString(FROM_CURRENCY_KEY, null)

        val toCurrencyCode = sharedPreferences
            .getString(TO_CURRENCY_KEY, null)

        var fromCurrency = CurrencyInfo(
            code = "USD",
            value = exchangeRates.rates
                .getValue(
                    key = "USD"
                )
        )

        var toCurrency = CurrencyInfo(
            code = "EUR",
            value = exchangeRates.rates
                .getValue(
                    key = "EUR"
                )
        )

        if (fromCurrencyCode != null) {
            fromCurrency = CurrencyInfo(
                code = fromCurrencyCode,
                value = exchangeRates.rates.getValue(key = fromCurrencyCode)
            )
        }

        if (toCurrencyCode != null) {
            toCurrency = CurrencyInfo(
                code = toCurrencyCode,
                value = exchangeRates.rates.getValue(key = toCurrencyCode)
            )
        }

        return Pair(
            first = fromCurrency,
            second = toCurrency
        )
    }

    fun onFromCurrencyChange(fromCurrency: CurrencyUIModel) {
        when(val validationResult = fromCurrency.value.toSafeDouble()) {
            is StringToDoubleConversionResult.Valid -> {
                with(receiver = uiState.value) {
                    val convertResult = convert(
                        fromBaseValue = exchangeRates.rates.getValue(key = fromCurrency.code),
                        toBaseValue = exchangeRates.rates.getValue(key = toCurrency.code),
                        amount = validationResult.value
                    )

                    _uiState.update {
                        it.copy(
                            fromCurrency = fromCurrency,
                            toCurrency = toCurrency.copy(
                                value = convertResult
                            ),
                            indicativeExchangeRate = "1 ${fromCurrency.code} = " +
                                    getIndicativeExchangeRate(
                                        fromCurrencyCode = fromCurrency.code,
                                        toCurrencyCode = toCurrency.code
                                    ) +
                                    " ${ toCurrency.code }"
                        )
                    }
                }

                sharedPreferences.edit().putString(FROM_CURRENCY_KEY, fromCurrency.code).apply()
            }

            StringToDoubleConversionResult.Empty -> {
                _uiState.update {
                    it.copy(
                        fromCurrency = it.fromCurrency.copy(value = ""),
                        toCurrency = it.toCurrency.copy(value = ""),
                    )
                }
            }

            StringToDoubleConversionResult.Invalid -> Unit
        }
    }

    fun onToCurrencyChange(toCurrency: CurrencyUIModel) {
        if (toCurrency.code != uiState.value.toCurrency.code) {
            _uiState.update {
                it.copy(
                    toCurrency = toCurrency
                )
            }
            onFromCurrencyChange(
                fromCurrency = uiState.value.fromCurrency
            )
            return
        }

        when(val validationResult = toCurrency.value.toSafeDouble()) {
            is StringToDoubleConversionResult.Valid -> {
                with(receiver = uiState.value) {
                    val convertResult = convert(
                        fromBaseValue = exchangeRates.rates.getValue(key = toCurrency.code),
                        toBaseValue = exchangeRates.rates.getValue(key = fromCurrency.code),
                        amount = validationResult.value
                    )

                    _uiState.update {
                        it.copy(
                            fromCurrency = fromCurrency.copy(
                                value = convertResult
                            ),
                            toCurrency = toCurrency
                        )
                    }
                }

                sharedPreferences.edit().putString(TO_CURRENCY_KEY, toCurrency.code).apply()
            }

            StringToDoubleConversionResult.Empty -> {
                _uiState.update {
                    it.copy(
                        fromCurrency = it.fromCurrency.copy(value = ""),
                        toCurrency = it.toCurrency.copy(value = ""),
                    )
                }
            }

            StringToDoubleConversionResult.Invalid -> Unit
        }
    }

    fun swapCurrencies() {
        _uiState.update {
            it.copy(
                fromCurrency = it.toCurrency,
                toCurrency = it.fromCurrency
            )
        }
    }

    private fun getIndicativeExchangeRate(
        fromCurrencyCode: String,
        toCurrencyCode: String,
    ): String =
        convert(
            fromBaseValue = exchangeRates.rates.getValue(key = fromCurrencyCode),
            toBaseValue = exchangeRates.rates.getValue(key = toCurrencyCode),
            amount = 1.0
        )

    private fun convert(
        fromBaseValue: Double,
        toBaseValue: Double,
        amount: Double
    ): String =
        ((toBaseValue / fromBaseValue ) * amount)
            .let {
                String
                    .format(
                        locale = Locale.ENGLISH,
                        format = "%.2f",
                        it
                    )
        }

    private fun fromDate(date: String) =
        try {
            val inputFormat = SimpleDateFormat("dd-MM-yyyy", Locale.ENGLISH)
            val parsedDate: Date? = inputFormat.parse(date)
            parsedDate?.let { inputFormat.format(it) } ?: ""
        } catch (e: Exception) {
            e.printStackTrace()
            ""
        }

    private fun String.toSafeDouble(): StringToDoubleConversionResult =
        if (this.endsWith(suffix = ".")) {
            StringToDoubleConversionResult.Valid(
                value = dropLast(1).toDouble()
            )
        } else if (isEmpty()) {
            StringToDoubleConversionResult.Empty
        } else {
            this.toDoubleOrNull()?.let {
                StringToDoubleConversionResult.Valid(it)
            } ?: StringToDoubleConversionResult.Invalid
        }

}

private sealed interface StringToDoubleConversionResult {
    data class Valid(val value: Double) : StringToDoubleConversionResult
    data object Invalid : StringToDoubleConversionResult
    data object Empty : StringToDoubleConversionResult
}