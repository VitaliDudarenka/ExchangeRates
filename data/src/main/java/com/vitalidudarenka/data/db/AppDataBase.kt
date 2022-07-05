package com.vitalidudarenka.data.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.vitalidudarenka.data.db.dao.RateDao
import com.vitalidudarenka.data.db.entities.RateDB

@Database(entities = [RateDB::class], version = 1, exportSchema = false)
abstract class AppDataBase : RoomDatabase() {
    companion object {
        private const val DATABASE_NAME = "ExchangeRatesLocalBase"
        fun getInstance(context: Context): AppDataBase {
            return Room.databaseBuilder(context, AppDataBase::class.java, DATABASE_NAME)
                .fallbackToDestructiveMigration().build()
        }
    }

    abstract fun getRateDao(): RateDao

}