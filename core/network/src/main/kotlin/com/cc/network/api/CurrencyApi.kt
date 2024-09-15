package com.cc.network.api

import com.cc.network.model.ExchangeRatesResponse
import retrofit2.http.GET

interface CurrencyApi {

    @GET("latest")
    suspend fun getExchangeRates(): ExchangeRatesResponse

}