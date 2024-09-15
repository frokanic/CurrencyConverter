package com.cc.database.db

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.cc.database.converter.CurrencyRatesConverter
import com.cc.database.dao.CurrencyDao
import com.cc.database.model.ExchangeRatesEntity

@Database(
    entities = [ExchangeRatesEntity::class],
    version = 1
)
@TypeConverters(CurrencyRatesConverter::class)
abstract class CurrencyDatabase: RoomDatabase() {

    abstract fun dao(): CurrencyDao

    companion object {
        const val NAME = "currency_db"
    }

}