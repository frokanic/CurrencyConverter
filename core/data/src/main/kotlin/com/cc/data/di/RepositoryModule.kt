package com.cc.data.di

import com.cc.data.repository.OfflineFirstCurrencyRepository
import com.cc.data.repository.OfflineFirstCurrencyRepositoryImpl
import com.cc.database.db.CurrencyDatabase
import com.cc.network.api.CurrencyApi
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {

    @Provides
    fun provideCurrencyRepositoryImplementation(
        database: CurrencyDatabase,
        api: CurrencyApi
    ): OfflineFirstCurrencyRepository =
        OfflineFirstCurrencyRepositoryImpl(
            database = database,
            api = api
        )

}