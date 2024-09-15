package com.frokanic.currencyconverter.di

import android.app.Application
import android.content.Context.MODE_PRIVATE
import android.content.SharedPreferences
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideDefaultSharedPref(
        application: Application
    ): SharedPreferences =
        application.getSharedPreferences(
            "defaultPref",
            MODE_PRIVATE
        )

}