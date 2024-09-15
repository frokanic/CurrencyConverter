package com.cc.network.di

import com.cc.network.api.CurrencyApi
import com.cc.network.interceptor.HeadersInterceptor
import com.cc.network.util.NetworkConstants.API_KEY
import com.cc.network.util.NetworkConstants.API_KEY_NAME
import com.cc.network.util.NetworkConstants.CURRENCY_BASE_URL
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.get
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.create
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    @Singleton
    @Provides
    fun provideOkHttpClient(): OkHttpClient {
        val loggingInterceptor = HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BASIC
        }

        return OkHttpClient
            .Builder()
            .addNetworkInterceptor(
                interceptor = loggingInterceptor
            )
            .addNetworkInterceptor(
                HeadersInterceptor(
                    headers = mapOf(
                        API_KEY_NAME to API_KEY
                    )
                )
            )
            .build()
    }

    @Singleton
    @Provides
    fun provideCurrencyApi(
        okHttpClient: OkHttpClient
    ): CurrencyApi =
        Retrofit
            .Builder()
            .baseUrl(CURRENCY_BASE_URL)
            .client(okHttpClient)
            .addConverterFactory(Json.asConverterFactory("application/json".toMediaType()))
            .build()
            .create()

}