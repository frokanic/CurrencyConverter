package com.cc.database.di

import android.app.Application
import androidx.room.Room
import com.cc.database.db.CurrencyDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Provides
    @Singleton
    fun provideCurrencyDatabase(
        application: Application
    ) =
        Room.databaseBuilder(
            context = application,
            klass = CurrencyDatabase::class.java,
            name = CurrencyDatabase.NAME
        )
            .fallbackToDestructiveMigration()
            .build()

}