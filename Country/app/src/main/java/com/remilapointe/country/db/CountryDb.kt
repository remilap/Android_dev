package com.remilapointe.country.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.remilapointe.country.dao.CountryDao
import com.remilapointe.country.entity.Country

@Database(entities = arrayOf(Country::class), version = 1, exportSchema = false)
abstract class CountryDb : RoomDatabase() {
    abstract fun countryDao(): CountryDao

    companion object {
        private var INSTANCE: CountryDb? = null

        fun getInstance(context: Context): CountryDb {
            if (INSTANCE == null) {
                synchronized(CountryDb::class) {
                    INSTANCE = buildDatabase(context)
                        .build()
                }
            }
            return INSTANCE!!
        }

        fun destroyInstance() {
            INSTANCE = null
        }

        private fun buildDatabase(context: Context) =
            Room.databaseBuilder(context.applicationContext, CountryDb::class.java, "country.db")

    }

}
